package com.example.ro_en.quicklib.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ro_en.quicklib.DisplayBookActivity;
import com.example.ro_en.quicklib.R;
import com.example.ro_en.quicklib.model.ShortBook;

import java.util.List;


public class ShortBookAdapter extends RecyclerView.Adapter<ShortBookAdapter.ViewHolder> {

    public List<ShortBook> shortBookList;
    public Context context;

    public ShortBookAdapter(Context context, List<ShortBook> shortBookList) {
        this.shortBookList = shortBookList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.titletext.setText(shortBookList.get(position).getBookName());
        final String bookId = shortBookList.get(position).getListId();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DisplayBookActivity.class);
                i.putExtra("bookId", bookId);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return shortBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView titletext;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            titletext = (TextView) mView.findViewById(R.id.bookTitleItem);

        }
    }
}
