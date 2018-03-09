package com.example.ro_en.quicklib;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by thoma on 09.03.2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<BookShelf> bookShelfList;
    private View.OnLongClickListener longClickListener;


    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_main, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        BookShelf bookShelf = bookShelfList.get(position);
        holder.listNameItem.setText(bookShelf.getListName());
    }

    @Override
    public int getItemCount() {
        return bookShelfList.size();
    }

    public void addItems(List<BookShelf> bookShelfList){
        this.bookShelfList = bookShelfList;
        notifyDataSetChanged();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView listNameItem;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            listNameItem = (TextView) itemView.findViewById(R.id.listNameitem);
        }
    }
}
