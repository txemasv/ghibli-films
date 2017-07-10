package org.app.txema.ghiblifilms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.app.txema.ghiblifilms.model.Film;

/**
 * Created by Txema on 05/07/2017.
 */

public class DetailsFragment extends Fragment {

    private TextView descriptionView;
    private TextView titleView;
    private TextView releaseDateView;
    private ImageView posterView;
    private TextView directorView;
    private TextView producerView;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_details, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        //Declare layout
        descriptionView = (TextView) view.findViewById(R.id.description);
        titleView = (TextView) view.findViewById(R.id.title);
        releaseDateView = (TextView) view.findViewById(R.id.release_date);
        posterView = (ImageView) view.findViewById(R.id.poster);
        directorView = (TextView) view.findViewById(R.id.director_name);
        producerView = (TextView) view.findViewById(R.id.producer_name);
    }

    public void updateDetailsView(Film film) {
        if (film != null) {
            //Insert film data into layout
            descriptionView.setText(film.getDescription());
            titleView.setText(film.getTitle());
            releaseDateView.setText(film.getReleaseDate());
            try {
                Glide.with(this).load(film.getPoster(getActivity())).into(posterView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            directorView.setText(film.getDirector());
            producerView.setText(film.getProducer());
        }
    }
}
