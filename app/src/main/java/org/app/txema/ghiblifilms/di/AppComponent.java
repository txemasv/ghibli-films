package org.app.txema.ghiblifilms.di;

import org.app.txema.ghiblifilms.MainActivity;
import org.app.txema.ghiblifilms.rest.NetworkClient;
import org.app.txema.ghiblifilms.view.fragment.CharacterListFragment;
import org.app.txema.ghiblifilms.view.fragment.FilmListFragment;
import org.app.txema.ghiblifilms.view.fragment.LocationListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Txema on 18/09/2017.
 */

@Singleton
@Component(modules = NetworkClient.class)
public interface AppComponent {
    void inject(FilmListFragment fragment);
    void inject(CharacterListFragment fragment);
    void inject(LocationListFragment fragment);
    void inject(MainActivity activity);
}
