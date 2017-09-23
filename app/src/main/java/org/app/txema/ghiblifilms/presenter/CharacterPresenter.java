package org.app.txema.ghiblifilms.presenter;

import org.app.txema.ghiblifilms.contract.CharacterContract;
import org.app.txema.ghiblifilms.model.Character;
import org.app.txema.ghiblifilms.rest.NetworkApi;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Txema on 17/09/2017.
 */

public class CharacterPresenter implements CharacterContract.Presenter {

    private CompositeSubscription subscription = new CompositeSubscription();
    private NetworkApi api;
    private CharacterContract.View view;

    public CharacterPresenter(CharacterContract.View view, NetworkApi api) {
        this.view = view;
        this.api = api;
    }

    @Override
    public void loadData() {
        view.onDataStarted();

        subscription.add(api.getAllCharacters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Character>>() {
                    @Override
                    public void onCompleted() {
                        view.onDataCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onDataError(e);
                    }

                    @Override
                    public void onNext(List<Character> characters) {
                        view.showData(characters);
                    }
                }));

    }

    @Override
    public void onStop() {
        subscription.clear();
    }


}