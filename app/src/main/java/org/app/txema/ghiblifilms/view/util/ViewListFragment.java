package org.app.txema.ghiblifilms.view.util;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import org.app.txema.ghiblifilms.MainActivity;
import org.app.txema.ghiblifilms.R;

/**
 * Created by Txema on 23/09/2017.
 */

public abstract class ViewListFragment extends Fragment {

    protected static final String TAG = ViewListFragment.class.getSimpleName();


    protected Snackbar snackbar;
    protected View viewMain;

    protected void progressShow() {
        ((MainActivity)getActivity()).showProgress();
    }

    protected void progressHide() {
        ((MainActivity)getActivity()).hideProgress();
    }

    protected void responseError(String message) {
        snackbar = Snackbar
                .make(viewMain, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.reload, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        requestPresenterGetData();
                    }
                });
        snackbar.show();
    }

    protected abstract void requestPresenterGetData();
}