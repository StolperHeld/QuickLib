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

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {

    private List<Book> bookList;
    View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, isbn, author, publisher, publisherDate, publisherPlace, page;
        TextView SubjectTextView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_book);
            isbn = (TextView) view.findViewById(R.id.isbn_book);
            author = (TextView) view.findViewById(R.id.author_book);
            publisher = (TextView) view.findViewById(R.id.publisher_book);
            publisherDate = (TextView) view.findViewById(R.id.publisher_date_book);
            publisherPlace = (TextView) view.findViewById(R.id.publisher_place_book);
            page = (TextView) view.findViewById(R.id.page_book);
            SubjectTextView = (TextView) view.findViewById(R.id.title_book);
        }
    }


    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.title.setText(book.getBookTitle());
        holder.isbn.setText(book.getBookIsbn());
        holder.author.setText(book.getBookAuthor());
        holder.publisher.setText(book.getBookPublisher());
        holder.publisherDate.setText(book.getBookPublisherDate());
        holder.publisherPlace.setText(book.getBookPublisherPlace());
        holder.page.setText(book.getBookPages() + "");
        holder.SubjectTextView.setText(bookList.get(position).getBookTitle());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
