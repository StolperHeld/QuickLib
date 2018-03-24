package com.example.ro_en.quicklib;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;


import com.example.ro_en.quicklib.model.Lists;

import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by thoma on 18.03.2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Lists> listsList;
    View itemView;


    public class MyViewHolder extends ViewHolder {
        public TextView listName;
        TextView SubjectTextView;

        public MyViewHolder(View view) {
            super(view);
            listName = (TextView) view.findViewById(R.id.listName);
            SubjectTextView = (TextView)view.findViewById(R.id.listName);

        }
    }

    public ListAdapter(List<Lists> listsList) {
        this.listsList = listsList;
    }

/*
    public ListAdapter(List<Lists> listsList) {
        this.listsList = listsList;
    }
*/
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);


        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Lists lists = listsList.get(position);
        holder.SubjectTextView.setText(listsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listsList.size();
    }
}
