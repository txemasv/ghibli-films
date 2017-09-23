package org.app.txema.ghiblifilms.contract;

import org.app.txema.ghiblifilms.model.Film;

import java.util.List;

/**
 * Created by Txema on 17/09/2017.
 */

public interface FilmContract {

    interface View {
        void onDataStarted();
        void onDataCompleted();
        void showData(List<Film> films);
        void onDataError(Throwable e);
    }

    interface Presenter {
        void loadData();
        void onStop();
    }
}