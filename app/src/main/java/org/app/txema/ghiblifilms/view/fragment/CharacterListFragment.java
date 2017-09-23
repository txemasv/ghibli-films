package org.app.txema.ghiblifilms.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.app.txema.ghiblifilms.R;
import org.app.txema.ghiblifilms.contract.CharacterContract;
import org.app.txema.ghiblifilms.di.App;
import org.app.txema.ghiblifilms.model.Character;
import org.app.txema.ghiblifilms.presenter.CharacterPresenter;
import org.app.txema.ghiblifilms.rest.NetworkApi;
import org.app.txema.ghiblifilms.view.adapter.CharactersAdapter;
import org.app.txema.ghiblifilms.view.util.ViewFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Txema on 17/09/2017.
 */

public class CharacterListFragment extends ViewFragment implements CharacterContract.View {


    private RecyclerView recyclerView;
    private CharacterPresenter presenter;


    @Inject
    NetworkApi api;

    public CharacterListFragment() {
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        //Load data
        requestPresenterGetData();
    }

    @Override
    public void requestPresenterGetData() {
        presenter = new CharacterPresenter(this, api);
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
    public void showData(List<Character> characters) {
        //wait for debugger (NOT IN RUN MODE!)
        //android.os.Debug.waitForDebugger();
        //Log.d(TAG, "wait for debugger ");

        CharactersAdapter adapter = new CharactersAdapter(characters, getActivity());
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
