package org.app.txema.ghiblifilms;

import com.google.gson.stream.MalformedJsonException;

import org.app.txema.ghiblifilms.model.Film;
import org.app.txema.ghiblifilms.rest.ApiClient;
import org.app.txema.ghiblifilms.rest.ApiInterface;
import org.junit.Test;

import java.io.EOFException;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Local unit test, which will execute on the development machine (host).
 *
 * This test verify the json response from the web server.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JsonResponseUnitTest {

    /**
     * Validate empty response from web server.
     *
     * The response is empty, then call function throws java.io.EOFException
     *
     * @throws Exception
     */
    @Test
    public void validate_NotEmptyResponse_KO() throws Exception {
        //Create MockServer.
        MockWebServer mockWebServer = new MockWebServer();

        //Set response for Retrofit handle to simulate a correct result or an error.
        //The server returns an empty body
        mockWebServer.enqueue(new MockResponse().setBody(""));

        // Start the server.
        mockWebServer.start();

        //Declare the ApiClient.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Declare the ApiService
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Declare call method that should consume the MockResponse
        Call<List<Film>> call = service.getAllFilms();

        //Validate response
        try {
            //Get response
            call.execute();
            fail();
        } catch (EOFException ex) {
            //Assert expected exception
            assertEquals(EOFException.class, ex.getClass());
        }

        //Finish web server
        mockWebServer.shutdown();
    }

    /**
     * Validate malformed json response from web server.
     *
     * The response is not coded in json, then call function
     * throws com.google.gson.stream.MalformedJsonException.
     *
     * @throws Exception
     */
    @Test
    public void validate_JsonFormatResponse_KO() throws Exception {
        //Create MockServer.
        MockWebServer mockWebServer = new MockWebServer();

        //Set response for Retrofit handle to simulate a correct result or an error.
        mockWebServer.enqueue(new MockResponse().setBody("not json response from server"));

        // Start the server.
        mockWebServer.start();

        //Declare the ApiClient.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Declare the ApiService
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Declare call method that should consume the MockResponse
        Call<List<Film>> call = service.getAllFilms();

        //Validate response
        try {
            //Get response
            call.execute();
            fail();
        } catch (MalformedJsonException ex) {
            //Assert expected exception
            assertEquals(MalformedJsonException.class, ex.getClass());
        }

        //Finish web server
        mockWebServer.shutdown();
    }

    /**
     * Validate json state (data type) from web server.
     *
     * The response has not the correct data type
     * (is expected a JsonArray but is received a JsonObject).
     * Then call function throws java.lang.IllegalStateException.
     *
     * @throws Exception
     */
    @Test
    public void validate_JsonStateResponse_KO() throws Exception {
        //Create MockServer.
        MockWebServer mockWebServer = new MockWebServer();

        //Set response for Retrofit handle to simulate a correct result or an error.
        //Is mocked a JsonObject, but is expected a JsonArray
        mockWebServer.enqueue(new MockResponse().setBody(
                "{" +
                    "\"prop1\":\"value1\"," +
                    "\"prop2\":\"value2\"" +
                "}"));

        // Start the server.
        mockWebServer.start();

        //Declare the ApiClient.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Declare the ApiService
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Declare call method that should consume the MockResponse
        Call<List<Film>> call = service.getAllFilms();

        //Validate response
        try {
            //Get response
            call.execute();
            fail();
        } catch (IllegalStateException ex) {
            //Assert expected exception
            assertEquals(IllegalStateException.class, ex.getClass());
        }

        //Finish web server
        mockWebServer.shutdown();
    }

    /**
     * Validate json model from web server.
     *
     * The response doesn't correspond with object model (List<Film>).
     * The objects of the response are verified and the assert condition has to be false.
     *
     * @throws Exception
     */
    @Test
    public void validate_JsonModelResponse_KO() throws Exception {
        //Create MockServer.
        MockWebServer mockWebServer = new MockWebServer();

        //Set response for Retrofit handle to simulate a correct result or an error.
        //Is mocked a correct JsonArray but is not the model of app (List<Film>)
        mockWebServer.enqueue(new MockResponse().setBody("[" +
                        "{" +
                        "\"prop1\":\"value1\"," +
                        "\"prop2\":\"value2\"" +
                        "}," +
                        "{" +
                        "\"prop1\":\"value1\"," +
                        "\"prop2\":\"value2\"" +
                        "}" +
                "]"));

        // Start the server.
        mockWebServer.start();

        //Declare the ApiClient.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Declare the ApiService
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Declare call method that should consume the MockResponse
        Call<List<Film>> call = service.getAllFilms();

        //Validate response
        //Get the response
        Response<List<Film>> response = call.execute();

        //Validate test KO:
        //Incorrect parsing response to List<Film>
        boolean isListFilmsValid = false;
        for(Film film: response.body()) {
            isListFilmsValid = film.isValid();
        }

        //Condition must be false (test KO)
        assertEquals(isListFilmsValid, false);

        //Finish web server
        mockWebServer.shutdown();
    }

    /**
     * Validate the correct response from web server.
     *
     * 1. response body is not empty
     * 2. response body is Json format
     * 3. response data type has the correct structure
     * 4. response data is parsable to model (List<Film>)
     *
     * @throws Exception
     */
    @Test
    public void validate_CorrectResponse_OK() throws Exception {
        //Create MockServer.
        MockWebServer mockWebServer = new MockWebServer();

        //Set response for Retrofit handle to simulate a correct result or an error.
        //Is mocked the correct response type and model parsable List<Film>.
        mockWebServer.enqueue(new MockResponse().setBody("[" +
                "  {\n" +
                "    \"id\": \"2baf70d1-42bb-4437-b551-e5fed5a87abe\",\n" +
                "    \"title\": \"Castle in the Sky\",\n" +
                "    \"description\": \"The orphan Sheeta inherited a mysterious crystal that links her to the mythical sky-kingdom of Laputa. With the help of resourceful Pazu and a rollicking band of sky pirates, she makes her way to the ruins of the once-great civilization. Sheeta and Pazu must outwit the evil Muska, who plans to use Laputa's science to make himself ruler of the world.\",\n" +
                "    \"director\": \"Hayao Miyazaki\",\n" +
                "    \"producer\": \"Isao Takahata\",\n" +
                "    \"release_date\": \"1986\",\n" +
                "    \"rt_score\": \"95\",\n" +
                "    \"people\": [\n" +
                "      \"https://ghibliapi.herokuapp.com/people/\"\n" +
                "    ],\n" +
                "    \"species\": [\n" +
                "      \"https://ghibliapi.herokuapp.com/species/af3910a6-429f-4c74-9ad5-dfe1c4aa04f2\"\n" +
                "    ],\n" +
                "    \"locations\": [\n" +
                "      \"https://ghibliapi.herokuapp.com/locations/\"\n" +
                "    ],\n" +
                "    \"vehicles\": [\n" +
                "      \"https://ghibliapi.herokuapp.com/vehicles/\"\n" +
                "    ],\n" +
                "    \"url\": \"https://ghibliapi.herokuapp.com/films/2baf70d1-42bb-4437-b551-e5fed5a87abe\"\n" +
                "  }" +
                "]"));

        // Start the server.
        mockWebServer.start();

        //Declare the ApiClient.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Declare the ApiService
        ApiInterface service = retrofit.create(ApiInterface.class);

        //Declare call method that should consume the MockResponse
        Call<List<Film>> call = service.getAllFilms();

        //Validate response
        //Get the response
        Response<List<Film>> response = call.execute();

        //Validate test KO:
        //Incorrect parsing response to List<Film>
        boolean isListFilmsValid = false;
        for(Film film: response.body()) {
            isListFilmsValid = film.isValid();
        }

        //Condition must be true (test OK)
        assertEquals(isListFilmsValid, true);

        //Finish web server
        mockWebServer.shutdown();
    }

}