package org.app.txema.ghiblifilms.rest;

import org.app.txema.ghiblifilms.model.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Txema on 20/06/2017.
 */

public interface ApiInterface {

    @GET("films")
    Observable<List<Film>> getAllFilms();
}
