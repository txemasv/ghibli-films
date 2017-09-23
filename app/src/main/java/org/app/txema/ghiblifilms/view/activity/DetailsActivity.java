package org.app.txema.ghiblifilms.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.app.txema.ghiblifilms.R;
import org.app.txema.ghiblifilms.model.Character;
import org.app.txema.ghiblifilms.model.Film;
import org.app.txema.ghiblifilms.model.Location;
import org.app.txema.ghiblifilms.view.fragment.CharacterDetailsFragment;
import org.app.txema.ghiblifilms.view.fragment.FilmDetailsFragment;
import org.app.txema.ghiblifilms.view.fragment.LocationDetailsFragment;

import static org.app.txema.ghiblifilms.view.util.Tag.TAG_DETAILS_FRAGMENT;

/**
 * Created by Txema on 05/07/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //slide animation
        setAnimationSlideToLeft();

        // Insert detailsFragment in the activity layout
        insertDetailsFragment();

        //Declare ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        //Declare ActionBar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Screen orientation (one-pane: portrait)
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void insertDetailsFragment() {
        Fragment fragment = null;

        // Film Fragment to be placed
        Film film = getIntent().getParcelableExtra("film");
        if (film != null) {
            //Put title in ToolBar
            this.title = film.getTitle();

            // Create Fragment to insert (with params)
            Bundle bundle = new Bundle();
            bundle.putParcelable("film", film);

            // Set FragmentClass Arguments
            fragment = new FilmDetailsFragment();
            fragment.setArguments(bundle);
        }

        // Character Fragment to be placed
        Character character = getIntent().getParcelableExtra("character");
        if (character != null) {
            //Put title in ToolBar
            this.title = character.getName();

            // Create Fragment to insert (with params)
            Bundle bundle = new Bundle();
            bundle.putParcelable("character", character);

            // Set FragmentClass Arguments
            fragment = new CharacterDetailsFragment();
            fragment.setArguments(bundle);
        }

        // Character Fragment to be placed
        Location location = getIntent().getParcelableExtra("location");
        if (location != null) {
            //Put title in ToolBar
            this.title = location.getName();

            // Create Fragment to insert (with params)
            Bundle bundle = new Bundle();
            bundle.putParcelable("location", location);

            // Set FragmentClass Arguments
            fragment = new LocationDetailsFragment();
            fragment.setArguments(bundle);
        }

        // Add Fragment to the FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.details_fragment, fragment, TAG_DETAILS_FRAGMENT).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setAnimationSlideToRight();
    }

    private void setAnimationSlideToLeft() {
        //slide animation
        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
    }

    private void setAnimationSlideToRight() {
        //slide animation
        overridePendingTransition(R.anim.right_slide_out, R.anim.right_slide_in);
    }

}
