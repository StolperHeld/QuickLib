package com.example.ro_en.quicklib.firebase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.ro_en.quicklib.R;
import com.example.ro_en.quicklib.model.Rating;
import java.util.List;

public class RatingApdater extends RecyclerView.Adapter<RatingApdater.ViewHolder> {

    public List<Rating> ratingList;

    public RatingApdater(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ratingText.setText(ratingList.get(position).getText());
        holder.ratingBar.setRating(ratingList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView ratingText;
        public RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ratingText = (TextView) mView.findViewById(R.id.displayRatingText);
            ratingBar = (RatingBar) mView.findViewById(R.id.displayRatingBar);
        }
    }


}
