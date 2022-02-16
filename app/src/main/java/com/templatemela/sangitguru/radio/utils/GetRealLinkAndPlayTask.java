package com.templatemela.sangitguru.radio.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;

import com.templatemela.sangitguru.radio.IPlayerService;
import com.templatemela.sangitguru.radio.MainRadioHelper;
import com.templatemela.sangitguru.radio.Utils;
import com.templatemela.sangitguru.radio.station.DataRadioStation;

import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;

public class GetRealLinkAndPlayTask extends AsyncTask<Void, Void, String> {
    private WeakReference<Context> contextRef;
    private DataRadioStation station;
    private WeakReference<IPlayerService> playerServiceRef;

    private OkHttpClient httpClient;

    public GetRealLinkAndPlayTask(Context context, DataRadioStation station, IPlayerService playerService) {
        this.contextRef = new WeakReference<>(context);
        this.station = station;
        this.playerServiceRef = new WeakReference<>(playerService);

        MainRadioHelper radioDroidApp = (MainRadioHelper) context.getApplicationContext();
        httpClient = radioDroidApp.getHttpClient();
    }

    @Override
    protected String doInBackground(Void... params) {
        Context context = contextRef.get();
        if (context != null) {
            return Utils.getRealStationLink(httpClient, context.getApplicationContext(), station.StationUuid);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        IPlayerService playerService = playerServiceRef.get();
        if (result != null && playerService != null && !isCancelled()) {
            try {
                station.playableUrl = result;
                playerService.SetStation(station);
                playerService.Play(false);
            } catch (RemoteException exception) {
                exception.printStackTrace();
            }
        }
        super.onPostExecute(result);
    }
}
