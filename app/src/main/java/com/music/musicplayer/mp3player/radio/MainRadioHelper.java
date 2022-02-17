package com.music.musicplayer.mp3player.radio;

import android.app.UiModeManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;
import androidx.preference.PreferenceManager;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;


import com.music.musicplayer.mp3player.BuildConfig;
import com.music.musicplayer.mp3player.R;
import com.music.musicplayer.mp3player.radio.alarm.RadioAlarmManager;
import com.music.musicplayer.mp3player.radio.history.TrackHistoryRepository;
import com.music.musicplayer.mp3player.radio.players.mpd.MPDClient;
import com.music.musicplayer.mp3player.radio.station.live.metadata.TrackMetadataSearcher;
import com.music.musicplayer.mp3player.radio.proxy.ProxySettings;
import com.music.musicplayer.mp3player.radio.recording.RecordingsManager;
import com.music.musicplayer.mp3player.radio.utils.TvChannelManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainRadioHelper extends MultiDexApplication {

    private com.music.musicplayer.mp3player.radio.HistoryManager historyManager;
    private com.music.musicplayer.mp3player.radio.FavouriteManager favouriteManager;
    private RecordingsManager recordingsManager;
    private RadioAlarmManager alarmManager;
    private TvChannelManager tvChannelManager;

    private TrackHistoryRepository trackHistoryRepository;

    private MPDClient mpdClient;

    private CastHandler castHandler;

    private TrackMetadataSearcher trackMetadataSearcher;

    private ConnectionPool connectionPool;
    private OkHttpClient httpClient;

    private Interceptor testsInterceptor;

    public class UserAgentInterceptor implements Interceptor {

        private final String userAgent;

        public UserAgentInterceptor(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", userAgent)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        GoogleProviderHelper.use(getBaseContext());
        connectionPool = new ConnectionPool();
        rebuildHttpClient();
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(newHttpClientForPicasso()));
        Picasso picassoInstance = builder.build();
        Picasso.setSingletonInstance(picassoInstance);
        com.music.musicplayer.mp3player.radio.CountryCodeDictionary.getInstance().load(this);
        com.music.musicplayer.mp3player.radio.CountryFlagsLoader.getInstance();
        historyManager = new com.music.musicplayer.mp3player.radio.HistoryManager(this);
        favouriteManager = new com.music.musicplayer.mp3player.radio.FavouriteManager(this);
        recordingsManager = new RecordingsManager();
        alarmManager = new RadioAlarmManager(this);
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            tvChannelManager = new TvChannelManager(this);
            favouriteManager.addObserver(tvChannelManager);
        }

        trackHistoryRepository = new TrackHistoryRepository(this);

        mpdClient = new MPDClient(this);

        castHandler = new CastHandler();

        trackMetadataSearcher = new TrackMetadataSearcher(httpClient);

        recordingsManager.updateRecordingsList();
    }

    public void setTestsInterceptor(Interceptor testsInterceptor) {
        this.testsInterceptor = testsInterceptor;
    }

    public void rebuildHttpClient() {
        OkHttpClient.Builder builder = newHttpClient()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new UserAgentInterceptor("RadioDroid2/" + BuildConfig.VERSION_NAME));

        httpClient = builder.build();
    }

    public com.music.musicplayer.mp3player.radio.HistoryManager getHistoryManager() {
        return historyManager;
    }

    public com.music.musicplayer.mp3player.radio.FavouriteManager getFavouriteManager() {
        return favouriteManager;
    }

    public RecordingsManager getRecordingsManager() {
        return recordingsManager;
    }

    public RadioAlarmManager getAlarmManager() {
        return alarmManager;
    }

    public TrackHistoryRepository getTrackHistoryRepository() {
        return trackHistoryRepository;
    }

    public MPDClient getMpdClient() {
        return mpdClient;
    }

    public CastHandler getCastHandler() {
        return castHandler;
    }

    public TrackMetadataSearcher getTrackMetadataSearcher() {
        return trackMetadataSearcher;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public OkHttpClient.Builder newHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectionPool(connectionPool);

        if (testsInterceptor != null) {
            builder.addInterceptor(testsInterceptor);
        }

        if (!setCurrentOkHttpProxy(builder)) {
            Toast toast = Toast.makeText(this, getResources().getString(R.string.ignore_proxy_settings_invalid), Toast.LENGTH_SHORT);
            toast.show();
        }
        return com.music.musicplayer.mp3player.radio.Utils.enableTls12OnPreLollipop(builder);
    }

    public OkHttpClient.Builder newHttpClientWithoutProxy() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectionPool(connectionPool);

        if (testsInterceptor != null) {
            builder.addInterceptor(testsInterceptor);
        }

        return com.music.musicplayer.mp3player.radio.Utils.enableTls12OnPreLollipop(builder);
    }

    public boolean setCurrentOkHttpProxy(@NonNull OkHttpClient.Builder builder) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        ProxySettings proxySettings = ProxySettings.fromPreferences(sharedPref);
        if (proxySettings != null) {
            if (!com.music.musicplayer.mp3player.radio.Utils.setOkHttpProxy(builder, proxySettings)) {
                // proxy settings are not valid
                return false;
            }
        }
        return true;
    }

    private OkHttpClient newHttpClientForPicasso() {
        File cache = new File(getCacheDir(), "picasso-cache");
        if (!cache.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cache.mkdirs();
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new UserAgentInterceptor("RadioDroid2/" + BuildConfig.VERSION_NAME))
                .cache(new Cache(cache, Integer.MAX_VALUE));

        if (testsInterceptor != null) {
            builder.addInterceptor(testsInterceptor);
        }

        setCurrentOkHttpProxy(builder);

        return builder.build();
    }
}
