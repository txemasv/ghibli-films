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
import org.app.txema.ghiblifilms.model.Character;

/**
 * Created by Txema on 05/07/2017.
 */

public class CharacterDetailsFragment extends Fragment {

    private TextView nameView;
    private TextView genderView;
    private TextView ageView;
    private ImageView posterView;
    private TextView eyeColorView;
    private TextView hairColorView;

    // The onCreateView method is called when Fragment should create its View object hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_details_character, parent, false);
    }

    // This event is triggered soon after onCreateView()
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        //Declare layout
        nameView = (TextView) view.findViewById(R.id.name);
        genderView = (TextView) view.findViewById(R.id.gender);
        ageView = (TextView) view.findViewById(R.id.age);
        posterView = (ImageView) view.findViewById(R.id.poster);
        eyeColorView = (TextView) view.findViewById(R.id.eye_color);
        hairColorView = (TextView) view.findViewById(R.id.hair_color);

        //Get film from arguments
        Bundle bundle = getArguments();
        if(bundle != null) {
            Character character = bundle.getParcelable("character");
            updateDetailsView(character);
        }
    }

    public void updateDetailsView(Character character) {
        if (character != null) {
            //Insert film data into layout
            nameView.setText(character.getName());
            genderView.setText(character.getGender());
            ageView.setText(character.getAge());
            try {
                Glide.with(this).load(character.getPoster(getActivity())).centerCrop().into(posterView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            eyeColorView.setText(character.getEyeColor());
            hairColorView.setText(character.getHairColor());
        }
    }
}
