package org.app.txema.ghiblifilms.presenter;

import org.app.txema.ghiblifilms.contract.FilmContract;
import org.app.txema.ghiblifilms.model.Film;
import org.app.txema.ghiblifilms.rest.NetworkApi;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Txema on 17/09/2017.
 */

public class FilmPresenter implements FilmContract.Presenter {

    private CompositeSubscription subscription = new CompositeSubscription();
    private NetworkApi api;
    private FilmContract.View view;

    public FilmPresenter(FilmContract.View view, NetworkApi api) {
        this.view = view;
        this.api = api;
    }

    @Override
    public void loadData() {
        view.onDataStarted();

        subscription.add(api.getAllFilms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Film>>() {
                    @Override
                    public void onCompleted() {
                        view.onDataCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onDataError(e);
                    }

                    @Override
                    public void onNext(List<Film> films) {
                        view.showData(films);
                    }
                }));

    }

    @Override
    public void onStop() {
        subscription.clear();
    }


}