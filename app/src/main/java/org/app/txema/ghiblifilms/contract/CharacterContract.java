package org.app.txema.ghiblifilms.contract;

import org.app.txema.ghiblifilms.model.Character;

import java.util.List;

/**
 * Created by Txema on 17/09/2017.
 */

public interface CharacterContract {

    interface View {
        void onDataStarted();
        void onDataCompleted();
        void showData(List<Character> characters);
        void onDataError(Throwable e);
    }

    interface Presenter {
        void loadData();
        void onStop();
    }
}