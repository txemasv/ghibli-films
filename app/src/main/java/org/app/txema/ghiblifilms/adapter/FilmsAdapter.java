package org.app.txema.ghiblifilms.adapter;

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

import org.app.txema.ghiblifilms.DetailsActivity;
import org.app.txema.ghiblifilms.DetailsFragment;
import org.app.txema.ghiblifilms.R;
import org.app.txema.ghiblifilms.model.Film;

import java.util.List;

/**
 * Created by Txema on 21/06/2017.
 */

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.MyViewHolder> {

    private List<Film> films;
    private Context context;

    //1. create static ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        private View cardView;
        private ImageView thumbnail;
        private TextView title, releaseDate;
        MyViewHolder(View itemView) {
            super(itemView);
            //find view items from layout
            cardView = itemView.findViewById(R.id.card_view);
            title = (TextView) itemView.findViewById(R.id.title);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            releaseDate = (TextView) itemView.findViewById(R.id.release_date);
        }
    }

    //2. create constructor (context, listItems)
    public FilmsAdapter(List<Film> films, Context context) {
        this.films = films;
        this.context = context;
        if(!films.isEmpty()) {
            setDefaultFilmDetails(films.get(0));
        }
    }

    //3. override the 3 methods
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //load itemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        //return viewHolder
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //load item (Film)
        final Film film = films.get(position);

        //set params on view item
        holder.title.setText(film.getTitle());
        holder.releaseDate.setText(film.getReleaseDate());

        // loading album cover using Glide library
        Glide.with(context).load(film.getThumbnail(context)).into(holder.thumbnail);

        //add listeners on items
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails(film);
            }
        });
    }

    private void updateDetails(Film film) {
        // Capture the detail fragment from the activity layout
        DetailsFragment detailsFrag = (DetailsFragment)
                ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.detailsFragment);

        // If detailsFrag is available
        if (detailsFrag != null) {
            //two-pane layout
            detailsFrag.updateDetailsView(film);
        } else {
            //one-pane layout
            Intent nextActivity = new Intent(context, DetailsActivity.class);
            nextActivity.putExtra("film", film);
            context.startActivity(nextActivity);
        }
    }

    private void setDefaultFilmDetails(Film film) {
        // Capture the detail fragment from the activity layout
        DetailsFragment detailsFrag = (DetailsFragment)
                ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.detailsFragment);

        // If detailsFrag is available, we put a default film on right pane.
        if (detailsFrag != null) {
            //two-pane layout
            detailsFrag.updateDetailsView(film);
        }
    }

    @Override
    public int getItemCount() {
        return films.size();
    }
}
