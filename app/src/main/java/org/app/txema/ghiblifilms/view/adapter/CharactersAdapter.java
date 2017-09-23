package org.app.txema.ghiblifilms.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.app.txema.ghiblifilms.R;
import org.app.txema.ghiblifilms.model.Character;
import org.app.txema.ghiblifilms.view.activity.DetailsActivity;
import org.app.txema.ghiblifilms.view.fragment.CharacterDetailsFragment;

import java.util.List;

/**
 * Created by Txema on 21/06/2017.
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.MyViewHolder> {

    private List<Character> characters;
    private Context context;

    //1. create static ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        private View cardView;
        private ImageView thumbnail;
        private TextView name;
        MyViewHolder(View itemView) {
            super(itemView);
            //find view items from layout
            cardView = itemView.findViewById(R.id.card_view);
            name = (TextView) itemView.findViewById(R.id.name);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    //2. create constructor (context, listItems)
    public CharactersAdapter(List<Character> characters, Context context) {
        this.characters = characters;
        this.context = context;
        if(!characters.isEmpty()) {
            setDefaultCharacterDetails(characters.get(0));
        }
    }

    //3. override the 3 methods
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //load itemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_character, parent, false);

        //return viewHolder
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //load item (Character)
        final Character character = characters.get(position);

        //set params on view item
        holder.name.setText(character.getName());

        // loading album cover using Glide library
        Glide.with(context).load(character.getThumbnail(context)).centerCrop().into(holder.thumbnail);

        //add listeners on items
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails(character);
            }
        });
    }

    private void updateDetails(Character character) {
        // Capture the detail fragment from the activity layout
        CharacterDetailsFragment detailsFrag = (CharacterDetailsFragment)
                ((AppCompatActivity)context).getSupportFragmentManager().
                        findFragmentById(R.id.details_fragment);

        // If detailsFrag is available
        if (detailsFrag != null) {
            //two-pane layout
            detailsFrag.updateDetailsView(character);
        } else {
            //one-pane layout
            Intent nextActivity = new Intent(context, DetailsActivity.class);
            nextActivity.putExtra("character", character);
            context.startActivity(nextActivity);
        }
    }

    private void setDefaultCharacterDetails(Character character) {
        // Capture the detail fragment from the activity layout
        CharacterDetailsFragment detailsFrag = (CharacterDetailsFragment)
                ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        // If detailsFrag is available, we put a default film on right pane.
        if (detailsFrag != null) {
            //two-pane layout
            detailsFrag.updateDetailsView(character);
        }
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }
}
