package org.app.txema.ghiblifilms.rest;

import org.app.txema.ghiblifilms.model.Character;
import org.app.txema.ghiblifilms.model.Film;
import org.app.txema.ghiblifilms.model.Location;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Txema on 20/06/2017.
 */

public interface NetworkApi {

    @GET("films")
    Observable<List<Film>> getAllFilms();

    @GET("people")
    Observable<List<Character>> getAllCharacters();

    @GET("locations")
    Observable<List<Location>> getAllLocations();
}
