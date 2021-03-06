package org.app.txema.ghiblifilms.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.app.txema.ghiblifilms.R;
import org.app.txema.ghiblifilms.model.NavigationDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by Txema on 13/08/2017.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavigationDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    //1. create ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon_nav_draw_item);
        }
    }

    //2. create constructor (context, listItems)
    public NavigationDrawerAdapter(Context context, List<NavigationDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    //3. override the 3 methods
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.navigation_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavigationDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());

        //Select icon
        switch (position) {
            case NavigationDrawerItem.FILMS:
                Glide.with(context).load(R.drawable.ic_film).into(holder.icon);
                break;
            case NavigationDrawerItem.CHARACTERS:
                Glide.with(context).load(R.drawable.ic_face).into(holder.icon);
                break;
            case NavigationDrawerItem.LOCATIONS:
                Glide.with(context).load(R.drawable.ic_location).into(holder.icon);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
