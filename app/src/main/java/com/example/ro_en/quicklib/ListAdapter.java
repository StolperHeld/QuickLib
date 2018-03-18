package com.example.ro_en.quicklib;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by thoma on 18.03.2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Lists> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listName;

        public MyViewHolder(View view) {
            super(view);
            listName = (TextView) view.findViewById(R.id.listName);

        }
    }


    public ListAdapter(List<Lists> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Lists lists = moviesList.get(position);
        holder.listName.setText(lists.getName());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
