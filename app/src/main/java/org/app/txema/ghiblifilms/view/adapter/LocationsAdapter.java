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
import org.app.txema.ghiblifilms.model.Location;
import org.app.txema.ghiblifilms.view.activity.DetailsActivity;
import org.app.txema.ghiblifilms.view.fragment.LocationDetailsFragment;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Txema on 21/06/2017.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.MyViewHolder> {

    private List<Location> locations;
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
    public LocationsAdapter(List<Location> locations, Context context) {
        this.locations = locations;
        this.context = context;

        //Delete items without image
        for (Iterator<Location> i = locations.listIterator(); i.hasNext(); ) {
            Location location = i.next();
            int checkExistence = location.getPoster(context);
            if (checkExistence == 0 ) {
                i.remove();
            }
        }

        //set default image
        if(!locations.isEmpty()) {
            setDefaultCharacterDetails(locations.get(0));
        }
    }

    //3. override the 3 methods
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //load itemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_location, parent, false);

        //return viewHolder
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //load item (Location)
        final Location location = locations.get(position);

        //set params on view item
        holder.name.setText(location.getName());

        // loading album cover using Glide library
        Glide.with(context).load(location.getThumbnail(context)).centerCrop().into(holder.thumbnail);

        //add listeners on items
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails(location);
            }
        });
    }

    private void updateDetails(Location location) {
        // Capture the detail fragment from the activity layout
        LocationDetailsFragment detailsFrag = (LocationDetailsFragment)
                ((AppCompatActivity)context).getSupportFragmentManager().
                        findFragmentById(R.id.details_fragment);

        // If detailsFrag is available
        if (detailsFrag != null) {
            //two-pane layout
            detailsFrag.updateDetailsView(location);
        } else {
            //one-pane layout
            Intent nextActivity = new Intent(context, DetailsActivity.class);
            nextActivity.putExtra("location", location);
            context.startActivity(nextActivity);
        }
    }

    private void setDefaultCharacterDetails(Location location) {
        // Capture the detail fragment from the activity layout
        LocationDetailsFragment detailsFrag = (LocationDetailsFragment)
                ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        // If detailsFrag is available, we put a default film on right pane.
        if (detailsFrag != null) {
            //two-pane layout
            detailsFrag.updateDetailsView(location);
        }
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
