package org.app.txema.ghiblifilms.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        private ImageView poster;
        private TextView title, releaseDate;
        MyViewHolder(View itemView) {
            super(itemView);
            //find view items from layout
            title = (TextView) itemView.findViewById(R.id.title);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            releaseDate = (TextView) itemView.findViewById(R.id.release_date);
        }
    }

    //2. create constructor (context, listItems)
    public FilmsAdapter(List<Film> films, Context context) {
        this.films = films;
        this.context = context;
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
        Film film = films.get(position);

        //set params on view item
        holder.title.setText(film.getTitle());
        holder.releaseDate.setText(film.getReleaseDate());

        // loading album cover using Glide library
        Glide.with(context).load(film.getPoster(context)).into(holder.poster);

        //add listeners on items
    }

    @Override
    public int getItemCount() {
        return films.size();
    }
}
