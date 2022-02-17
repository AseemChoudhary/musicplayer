package com.music.musicplayer.mp3player.radio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.music.musicplayer.mp3player.BuildConfig;
import com.music.musicplayer.mp3player.R;
import com.music.musicplayer.mp3player.radio.station.ItemAdapterStation;
import com.music.musicplayer.mp3player.radio.station.DataRadioStation;
import com.music.musicplayer.mp3player.radio.station.ItemAdapterIconOnlyStation;
import com.music.musicplayer.mp3player.radio.interfaces.IAdapterRefreshable;
import com.music.musicplayer.mp3player.radio.station.StationActions;
import com.music.musicplayer.mp3player.radio.station.StationsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import okhttp3.OkHttpClient;

public class FragmentStarred extends Fragment implements IAdapterRefreshable, Observer {
    private static final String TAG = "FragmentStarred";

    private RecyclerView rvStations;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AsyncTask task = null;

    private FavouriteManager favouriteManager;

    void onStationClick(DataRadioStation theStation) {
        MainRadioHelper radioDroidApp = (MainRadioHelper) getActivity().getApplication();
        Utils.showPlaySelection(radioDroidApp, theStation, getActivity().getSupportFragmentManager());
    }

    public void RefreshListGui() {
        if (BuildConfig.DEBUG) Log.d(TAG, "refreshing the stations list.");

        ItemAdapterStation adapter = (ItemAdapterStation) rvStations.getAdapter();

        if (BuildConfig.DEBUG) Log.d(TAG, "stations count:" + favouriteManager.listStations.size());

        adapter.updateList(this, favouriteManager.listStations);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainRadioHelper radioDroidApp = (MainRadioHelper) requireActivity().getApplication();
        favouriteManager = radioDroidApp.getFavouriteManager();
        favouriteManager.addObserver(this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stations, container, false);
        rvStations = (RecyclerView) view.findViewById(R.id.recyclerViewStations);

        ItemAdapterStation adapter;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (sharedPref.getBoolean("load_icons", false) && sharedPref.getBoolean("icons_only_favorites_style", false)) {
            adapter = new ItemAdapterIconOnlyStation(getActivity(), R.layout.list_item_icon_only_station, StationsFilter.FilterType.LOCAL);
            Context ctx = getContext();
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            int itemWidth = (int) ctx.getResources().getDimension(R.dimen.regular_style_icon_container_width);
            int noOfColumns = displayMetrics.widthPixels / itemWidth;
            GridLayoutManager glm = new GridLayoutManager(ctx, noOfColumns);
            rvStations.setAdapter(adapter);
            rvStations.setLayoutManager(glm);
            ((ItemAdapterIconOnlyStation)adapter).enableItemMove(rvStations);
        } else {
            adapter = new ItemAdapterStation(getActivity(), R.layout.list_item_station, StationsFilter.FilterType.LOCAL);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(RecyclerView.VERTICAL);

            rvStations.setAdapter(adapter);
            rvStations.setLayoutManager(llm);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvStations.getContext(),
                    llm.getOrientation());
            rvStations.addItemDecoration(dividerItemDecoration);
            adapter.enableItemMoveAndRemoval(rvStations);
        }

        adapter.setStationActionsListener(new ItemAdapterStation.StationActionsListener() {
            @Override
            public void onStationClick(DataRadioStation station, int pos) {
                FragmentStarred.this.onStationClick(station);
            }

            @Override
            public void onStationSwiped(final DataRadioStation station) {
                StationActions.removeFromFavourites(requireContext(), getView(), station);
            }

            @Override
            public void onStationMoved(int from, int to) {
                favouriteManager.moveWithoutNotify(from, to);
            }

            @Override
            public void onStationMoveFinished() {
                // We don't want to update RecyclerView during its layout process
                requireView().post(() -> {
                    favouriteManager.Save();
                    favouriteManager.notifyObservers();
                });
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            if (BuildConfig.DEBUG) {
                                Log.d(TAG, "onRefresh called from SwipeRefreshLayout");
                            }
                            RefreshDownloadList();
                        }
                    }
            );
        }

        RefreshListGui();

        return view;
    }

    void RefreshDownloadList(){
        MainRadioHelper radioDroidApp = (MainRadioHelper) getActivity().getApplication();
        final OkHttpClient httpClient = radioDroidApp.getHttpClient();
        ArrayList<String> listUUids = new ArrayList<String>();
        for (DataRadioStation station : favouriteManager.listStations){
            listUUids.add(station.StationUuid);
        }
        Log.d(TAG, "Search for items: "+listUUids.size());

        task = new AsyncTask<Void, Void, List<DataRadioStation>>() {
            @Override
            protected List<DataRadioStation> doInBackground(Void... params) {
                return Utils.getStationsByUuid(httpClient, getActivity(), listUUids);
            }

            @Override
            protected void onPostExecute(List<DataRadioStation> result) {
                DownloadFinished();
                if(getContext() != null)
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(ActivityMain.ACTION_HIDE_LOADING));
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Download relativeUrl finished");
                }
                if (result != null) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "Download relativeUrl OK");
                    }
                    Log.d(TAG, "Found items: "+result.size());
                    SyncList(result);
                    RefreshListGui();
                } else {
                    try {
                        Toast toast = Toast.makeText(getContext(), getResources().getText(R.string.error_list_update), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    catch(Exception exception){
                        Log.e("ERR",exception.toString());
                    }
                }
                super.onPostExecute(result);
            }
        }.execute();
    }

    private void SyncList(List<DataRadioStation> list_new) {
        ArrayList<String> to_remove = new ArrayList<String>();
        for (DataRadioStation station_current: favouriteManager.listStations){
            boolean found = false;
            for (DataRadioStation station_new: list_new){
                if (station_new.StationUuid.equals(station_current.StationUuid)){
                    found = true;
                }
            }
            if (!found){
                Log.d(TAG,"Remove station: " + station_current.StationUuid + " - " + station_current.Name);
                to_remove.add(station_current.StationUuid);
                station_current.DeletedOnServer = true;
            }
        }
        Log.d(TAG,"replace items");
        favouriteManager.replaceList(list_new);
        Log.d(TAG,"fin save");

        if (to_remove.size() > 0) {
            Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.notify_sync_list_deleted_entries, to_remove.size(), favouriteManager.size()), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    protected void DownloadFinished() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        rvStations.setAdapter(null);

        MainRadioHelper radioDroidApp = (MainRadioHelper) requireActivity().getApplication();
        favouriteManager = radioDroidApp.getFavouriteManager();
        favouriteManager.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        RefreshListGui();
    }
}