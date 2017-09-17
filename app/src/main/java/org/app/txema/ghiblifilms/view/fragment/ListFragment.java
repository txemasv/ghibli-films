package org.app.txema.ghiblifilms.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.app.txema.ghiblifilms.R;
import org.app.txema.ghiblifilms.view.adapter.FilmsAdapter;
import org.app.txema.ghiblifilms.model.Film;
import org.app.txema.ghiblifilms.presenter.MainContract;
import org.app.txema.ghiblifilms.presenter.MainPresenter;
import org.app.txema.ghiblifilms.rest.ApiClient;

import java.util.List;

/**
 * Created by Txema on 17/09/2017.
 */

public class ListFragment extends Fragment implements MainContract.View {

    private static final String TAG = ListFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ProgressDialog pDialog;
    private View viewMain;
    private MainPresenter presenter;
    private Snackbar snackbar;

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
        doCallGetData();
    }

    public void doCallGetData() {
        //TODO: Inject api object with Dagger
        ApiClient api = new ApiClient();
        presenter = new MainPresenter(this, api);
        presenter.loadData();
    }

    @Override
    public void onDataStarted() {
        progressDialogShow();
    }

    @Override
    public void onDataCompleted() {
        progressDialogHide();
    }

    @Override
    public void showData(List<Film> films) {
        //wait for debugger (NOT IN RUN MODE!)
        //android.os.Debug.waitForDebugger();
        //Log.d(TAG, "wait for debugger ");

        FilmsAdapter adapter = new FilmsAdapter(films, getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDataError(Throwable e) {
        Log.e(TAG, e.getMessage());
        progressDialogHide();
        responseError(getString(R.string.http_error));
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    private void progressDialogShow() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.progress_dialog));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void progressDialogHide() {
        if (pDialog != null && pDialog.isShowing()) {
            try {
                pDialog.dismiss();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void responseError(String message) {
        snackbar = Snackbar
                .make(viewMain, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.reload, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        doCallGetData();
                    }
                });
        snackbar.show();
    }
}
