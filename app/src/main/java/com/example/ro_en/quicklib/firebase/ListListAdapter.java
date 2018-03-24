package com.example.ro_en.quicklib.firebase;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ro_en.quicklib.model.Lists;
import com.example.ro_en.quicklib.R;

import java.util.List;

/**
 * Created by RO_EN on 24.03.2018.
 */

public class ListListAdapter extends RecyclerView.Adapter<ListListAdapter.ViewHolder> {

    public List<Lists> listsList;
    public Context context;

    public ListListAdapter(Context context, List<Lists> listsList){
            this.listsList = listsList;
            this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameText.setText(listsList.get(position).getName());

        final String listId = listsList.get(position).listId;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "List-Id: "+ listId, Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return listsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView nameText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nameText = (TextView) mView.findViewById(R.id.listNameitem);
        }
    }
}
