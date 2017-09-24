package org.app.txema.ghiblifilms;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;

import org.app.txema.ghiblifilms.di.App;
import org.app.txema.ghiblifilms.model.NavigationDrawerItem;
import org.app.txema.ghiblifilms.view.fragment.AboutUsFragment;
import org.app.txema.ghiblifilms.view.fragment.CharacterDetailsFragment;
import org.app.txema.ghiblifilms.view.fragment.CharacterListFragmentList;
import org.app.txema.ghiblifilms.view.fragment.FilmDetailsFragment;
import org.app.txema.ghiblifilms.view.fragment.FilmListFragmentList;
import org.app.txema.ghiblifilms.view.fragment.LocationDetailsFragment;
import org.app.txema.ghiblifilms.view.fragment.LocationListFragmentList;
import org.app.txema.ghiblifilms.view.fragment.NavigationDrawerFragment;

import static org.app.txema.ghiblifilms.view.util.Tag.TAG_DETAILS_FRAGMENT;
import static org.app.txema.ghiblifilms.view.util.Tag.TAG_LIST_FRAGMENT;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.FragmentDrawerListener {

    private AdView mAdView;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        //AdMobs TODO
        //mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        //adMobListener();

        //DI with Dagger
        ((App) getApplication()).getAppComponent().inject(this);

        //Declare AppBarLayout
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        //Declare collapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.cover_title));

        //Declare ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //NavigationDrawerFragment declaration
        NavigationDrawerFragment navDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        navDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        navDrawerFragment.setDrawerListener(this);

        //Set image cover for collapsingLayout (Using Glide)
        try {
            Glide.with(this).load(R.drawable.cover).centerCrop().into((ImageView)findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setScreenOrientation();

        //insert fragment at right pane if layout is two panes
        if (getResources().getBoolean(R.bool.two_panes)) {
            insertDefaultDetailsFragment();
        }

        //insert fragment list
        insertFilmListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about_us) {
            DialogFragment newFragment = new AboutUsFragment();
            newFragment.show(getSupportFragmentManager(), DialogFragment.class.getSimpleName());
            return true;
        }

        if (id == R.id.action_rate_app) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=org.app.txema.ghiblifilms"));
            startActivity(browserIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setScreenOrientation() {
        if (getResources().getBoolean(R.bool.two_panes)) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        if (position != NavigationDrawerItem.SELECTED) {
            switch (position) {
                case NavigationDrawerItem.FILMS:
                    if (getResources().getBoolean(R.bool.two_panes)) {
                        insertFilmDetailsFragment();
                    }
                    insertFilmListFragment();
                    break;
                case NavigationDrawerItem.CHARACTERS:
                    if (getResources().getBoolean(R.bool.two_panes)) {
                        insertCharacterDetailsFragment();
                    }
                    insertCharacterListFragment();
                    break;
                case NavigationDrawerItem.LOCATIONS:
                    if (getResources().getBoolean(R.bool.two_panes)) {
                        insertLocationDetailsFragment();
                    }
                    insertLocationsListFragment();
                    break;
            }
            NavigationDrawerItem.SELECTED = position;
        }
    }

    // *****************************************
    //  FRAGMENTS TO INSERT: 1) DetailsFragment
    // *****************************************

    //default details fragment
    private void insertDefaultDetailsFragment() {
        // Create a new Fragment to be placed in the activity layout
        FilmDetailsFragment fragment = new FilmDetailsFragment();

        // Add the fragment to the 'fragment_details' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.details_fragment, fragment, TAG_DETAILS_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    //film details fragment
    private void insertFilmDetailsFragment() {
        // Create a new Fragment to be placed in the activity layout
        FilmDetailsFragment fragment = new FilmDetailsFragment();

        // Replace the fragment to the 'fragment_details' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_fragment, fragment, TAG_DETAILS_FRAGMENT)
                .commit();

    }

    //character details fragment
    private void insertCharacterDetailsFragment() {
        // Create a new Fragment to be placed in the activity layout
        CharacterDetailsFragment fragment = new CharacterDetailsFragment();

        // Replace the fragment to the 'fragment_details' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_fragment, fragment, TAG_DETAILS_FRAGMENT)
                .commit();
    }

    //locations details fragment
    private void insertLocationDetailsFragment() {
        // Create a new Fragment to be placed in the activity layout
        LocationDetailsFragment fragment = new LocationDetailsFragment();

        // Replace the fragment to the 'fragment_details' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_fragment, fragment, TAG_DETAILS_FRAGMENT)
                .commit();
    }

    // *****************************************
    //  FRAGMENTS TO INSERT: 2) ListFragment
    // *****************************************

    //films list fragment
    private void insertFilmListFragment() {
        // Create a new Fragment to be placed in the activity layout
        FilmListFragmentList fragment = new FilmListFragmentList();

        // Add the fragment to the 'fragment_list' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_fragment, fragment, TAG_LIST_FRAGMENT)
                .commit();
    }

    //characters list fragment
    private void insertCharacterListFragment() {
        // Create a new Fragment to be placed in the activity layout
        CharacterListFragmentList fragment = new CharacterListFragmentList();

        // Add the fragment to the 'fragment_list' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_fragment, fragment, TAG_LIST_FRAGMENT).commit();
    }

    //locations list fragment
    private void insertLocationsListFragment() {
        // Create a new Fragment to be placed in the activity layout
        LocationListFragmentList fragment = new LocationListFragmentList();

        // Add the fragment to the 'fragment_list' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_fragment, fragment, TAG_LIST_FRAGMENT).commit();
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void adMobListener() {
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.i("Ads", "onAdClosed");
            }
        });
    }
}
