package org.app.txema.ghiblifilms.di;

import android.app.Application;

import org.app.txema.ghiblifilms.rest.ApiClient;

/**
 * Created by Txema on 18/09/2017.
 */

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger%COMPONENT_NAME%
        appComponent = DaggerAppComponent.builder()
                .apiClient(new ApiClient())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
