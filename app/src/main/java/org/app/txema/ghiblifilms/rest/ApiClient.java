package org.app.txema.ghiblifilms.rest;

import org.app.txema.ghiblifilms.model.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Txema on 20/06/2017.
 */

public class ApiClient implements ApiInterface {
    public static final String BASE_URL = "https://ghibliapi.herokuapp.com/";

    private ApiInterface api;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.api = retrofit.create(ApiInterface.class);
    }

    @Override
    public Observable<List<Film>> getAllFilms() {
        return this.api.getAllFilms();
    }
}
