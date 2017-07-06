package org.app.txema.ghiblifilms;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.app.txema.ghiblifilms.model.Film;

/**
 * Created by Txema on 05/07/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //slide animation
        setAnimationSlideToLeft();

        //Get Film from extras
        Film film = getIntent().getParcelableExtra("film");

        //Declare ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(film.getTitle());
        setSupportActionBar(toolbar);

        //Declare ActionBar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Insert film data into layout
        final TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(film.getDescription());

        final TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(film.getTitle());

        final TextView releaseDateView = (TextView) findViewById(R.id.release_date);
        releaseDateView.setText(film.getReleaseDate());

        try {
            Glide.with(this).load(film.getPoster(DetailsActivity.this)).into((ImageView) findViewById(R.id.poster));
        } catch (Exception e) {
            e.printStackTrace();
        }

        final TextView directorView = (TextView) findViewById(R.id.director_name);
        directorView.setText(film.getDirector());

        final TextView producerView = (TextView) findViewById(R.id.producer_name);
        producerView.setText(film.getProducer());
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
