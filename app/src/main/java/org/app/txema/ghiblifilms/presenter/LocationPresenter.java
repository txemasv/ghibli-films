package org.app.txema.ghiblifilms.presenter;

import org.app.txema.ghiblifilms.contract.LocationContract;
import org.app.txema.ghiblifilms.model.Location;
import org.app.txema.ghiblifilms.rest.NetworkApi;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Txema on 17/09/2017.
 */

public class LocationPresenter implements LocationContract.Presenter {

    private CompositeSubscription subscription = new CompositeSubscription();
    private NetworkApi api;
    private LocationContract.View view;

    public LocationPresenter(LocationContract.View view, NetworkApi api) {
        this.view = view;
        this.api = api;
    }

    @Override
    public void loadData() {
        view.onDataStarted();

        subscription.add(api.getAllLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Location>>() {
                    @Override
                    public void onCompleted() {
                        view.onDataCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onDataError(e);
                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        view.showData(locations);
                    }
                }));

    }

    @Override
    public void onStop() {
        subscription.clear();
    }


}