package org.app.txema.ghiblifilms.di;

import org.app.txema.ghiblifilms.MainActivity;
import org.app.txema.ghiblifilms.rest.ApiClient;
import org.app.txema.ghiblifilms.view.fragment.ListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Txema on 18/09/2017.
 */

@Singleton
@Component(modules = ApiClient.class)
public interface AppComponent {
    void inject(ListFragment fragment);
    void inject(MainActivity activity);
}
