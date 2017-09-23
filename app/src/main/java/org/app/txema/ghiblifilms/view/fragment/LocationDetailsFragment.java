package org.app.txema.ghiblifilms.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.app.txema.ghiblifilms.R;
import org.app.txema.ghiblifilms.model.Location;

/**
 * Created by Txema on 05/07/2017.
 */

public class LocationDetailsFragment extends Fragment {

    private TextView nameView;
    private TextView climateView;
    private TextView terrainView;
    private ImageView posterView;
    private TextView surfaceWaterView;

    // The onCreateView method is called when Fragment should create its View object hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_details_location, parent, false);
    }

    // This event is triggered soon after onCreateView()
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        //Declare layout
        nameView = (TextView) view.findViewById(R.id.name);
        climateView = (TextView) view.findViewById(R.id.climate);
        terrainView = (TextView) view.findViewById(R.id.terrain);
        posterView = (ImageView) view.findViewById(R.id.poster);
        surfaceWaterView = (TextView) view.findViewById(R.id.surface_water);

        //Get film from arguments
        Bundle bundle = getArguments();
        if(bundle != null) {
            Location location = bundle.getParcelable("location");
            updateDetailsView(location);
        }
    }

    public void updateDetailsView(Location location) {
        if (location != null) {
            //Insert film data into layout
            nameView.setText(location.getName());
            climateView.setText(location.getClimate());
            terrainView.setText(location.getTerrain());
            try {
                Glide.with(this).load(location.getPoster(getActivity())).centerCrop().into(posterView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            surfaceWaterView.setText(location.getSurfaceWater());
        }
    }
}
