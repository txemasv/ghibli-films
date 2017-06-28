package org.app.txema.ghiblifilms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
    private CoordinatorLayout layoutMain;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Declare collapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.cover_title));

        //Declare main layout for use with snackBar
        layoutMain = (CoordinatorLayout) findViewById(R.id.activity_main_layout);

        //Inflate recyclerView layout
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Use the layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        //Set image cover for collapsingLayout (Using Glide)
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Declare ApiInterface Service
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        //Declare Call function
        Call<List<Film>> call = apiService.getAllFilms();

        //Call server (onResponse, onFailure)
        progressDialogShow();
        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                //wait for debugger (NOT IN RUN MODE!)
                //android.os.Debug.waitForDebugger();
               // Log.d(TAG, "wait for debugger ");

                //get status_code
                int statusCode = response.code();

                //verify response http (code)
                //Api Webservice specification: codes 200(OK), 400(BAD_REQUEST), 404(NOT_FOUND)
                switch (statusCode) {
                    case ResponseCode.OK:
                        //1. get items from response
                        List<Film> films = response.body();
                        //2. specify adapter, with items list
                        adapter = new FilmsAdapter(films, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        progressDialogDismiss();
                        break;
                    case ResponseCode.BAD_REQUEST:
                        Log.e(TAG, "HTTP ERROR 400: BAD_REQUEST");
                        responseError("The server cannot or will not process the request due to an apparent client error.");
                        break;
                    case ResponseCode.NOT_FOUND:
                        Log.e(TAG, "HTTP ERROR 404: NOT_FOUND");
                        responseError("The requested resource could not be found.");
                        break;
                    default:
                        Log.e(TAG, "HTTP ERROR " + statusCode);
                        responseError("Error Http " + statusCode);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                responseError("Failed to connect to webservice.");
            }
        });
    }

    private void responseError(String message) {
        progressDialogDismiss();
        Snackbar snackbar = Snackbar
                .make(layoutMain, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }


    private void progressDialogShow() {
        // Showing progress dialog
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void progressDialogDismiss() {
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
