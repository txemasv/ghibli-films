package org.app.txema.ghiblifilms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.app.txema.ghiblifilms.adapter.FilmsAdapter;
import org.app.txema.ghiblifilms.model.Film;
import org.app.txema.ghiblifilms.rest.ApiClient;
import org.app.txema.ghiblifilms.rest.ApiInterface;
import org.app.txema.ghiblifilms.util.ResponseCode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private FilmsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. Declare layout  variables
        //main layout for snackBar

        //recyclerView layout
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //2. Use the layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        //3. Declare ApiInterface Service
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        //4. Declare Call function
        Call<List<Film>> call = apiService.getAllFilms();

        //5. Call server (onResponse, onFailure)
        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                //1. wait for debugger (NOT IN RUN MODE!)
                android.os.Debug.waitForDebugger();
                Log.d(TAG, "wait for debugger ");

                //2. get status_code
                int statusCode = response.code();

                //3. verify response http (code)
                //App codes 200(OK), 400(BAD_REQUEST), 404(NOT_FOUND)
                switch (statusCode) {
                    case ResponseCode.OK:
                        //1. get items from response
                        List<Film> films = response.body();
                        //2. specify adapter, with items list
                        adapter = new FilmsAdapter(films, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        break;
                    case ResponseCode.BAD_REQUEST:
                        Log.e(TAG, "ERROR 400 BAD_REQUEST");
                        break;
                    case ResponseCode.NOT_FOUND:
                        Log.e(TAG, "ERROR 404 NOT_FOUND");
                        break;
                    default:
                        Log.e(TAG, "ERROR " + statusCode);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
