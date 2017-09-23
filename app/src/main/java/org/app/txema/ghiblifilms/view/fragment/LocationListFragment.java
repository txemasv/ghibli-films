package org.app.txema.ghiblifilms.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.app.txema.ghiblifilms.R;
import org.app.txema.ghiblifilms.contract.LocationContract;
import org.app.txema.ghiblifilms.di.App;
import org.app.txema.ghiblifilms.model.Location;
import org.app.txema.ghiblifilms.presenter.LocationPresenter;
import org.app.txema.ghiblifilms.rest.NetworkApi;
import org.app.txema.ghiblifilms.view.adapter.LocationsAdapter;
import org.app.txema.ghiblifilms.view.util.ViewFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Txema on 17/09/2017.
 */

public class LocationListFragment extends ViewFragment implements LocationContract.View {

    private static final String TAG = LocationListFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private LocationPresenter presenter;

    @Inject
    NetworkApi api;

    public LocationListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //DI with Dagger
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
    }

    // onCreateView method is called when Fragment should create its View object hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_list, parent, false);
    }

    // event is triggered soon after onCreateView()
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        //Declare main layout for use with snackBar
        viewMain = view.findViewById(R.id.fragment_list);

        //Inflate recyclerView layout
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Use the layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //Load data
        requestPresenterGetData();
    }

    public void requestPresenterGetData() {
        presenter = new LocationPresenter(this, api);
        presenter.loadData();
    }

    @Override
    public void onDataStarted() {
        progressShow();
    }

    @Override
    public void onDataCompleted() {
        progressHide();
    }

    @Override
    public void showData(List<Location> locations) {
        //wait for debugger (NOT IN RUN MODE!)
        //android.os.Debug.waitForDebugger();
        //Log.d(TAG, "wait for debugger ");

        LocationsAdapter adapter = new LocationsAdapter(locations, getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDataError(Throwable e) {
        Log.e(TAG, e.getMessage());
        progressHide();
        responseError(getString(R.string.http_error));
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }
}
