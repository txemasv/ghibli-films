package org.app.txema.ghiblifilms.di;

import org.app.txema.ghiblifilms.MainActivity;
import org.app.txema.ghiblifilms.rest.NetworkClient;
import org.app.txema.ghiblifilms.view.fragment.CharacterListFragmentList;
import org.app.txema.ghiblifilms.view.fragment.FilmListFragmentList;
import org.app.txema.ghiblifilms.view.fragment.LocationListFragmentList;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Txema on 18/09/2017.
 */

@Singleton
@Component(modules = NetworkClient.class)
public interface AppComponent {
    void inject(FilmListFragmentList fragment);
    void inject(CharacterListFragmentList fragment);
    void inject(LocationListFragmentList fragment);
    void inject(MainActivity activity);
}
