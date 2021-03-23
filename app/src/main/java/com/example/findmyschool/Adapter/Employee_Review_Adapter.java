package com.example.findmyschool.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Employee.Employee_Review_Activity;
import com.example.findmyschool.Model.Review;
import com.example.findmyschool.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;

import java.util.ArrayList;

public class Employee_Review_Adapter extends RecyclerView.Adapter<Employee_Review_Adapter.ViewHolder> {
    Activity activity;
    ArrayList<Review> reviews;
    LayoutInflater inflater;

    public Employee_Review_Adapter(Activity employee_review_activity, ArrayList<Review> reviews) {
        this.activity = employee_review_activity;
        this.reviews = reviews;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.review_name.setText(reviews.get(position).getFromname());
        holder.review.setText(reviews.get(position).getReview());
        holder.date.setText(reviews.get(position).getDate());
        holder.rating.setRating(Float.parseFloat(reviews.get(position).getStar()));
        Glide.with(activity).load(reviews.get(position).getFromprofile()).into(holder.review_profile);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setData(ArrayList<Review> reviews_list) {
        this.reviews = reviews_list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RatingBar rating;
        ShapeableImageView review_profile;
        TextView review_name, date, review;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            review_profile = (ShapeableImageView) itemView.findViewById(R.id.review_profile);
            review = (TextView) itemView.findViewById(R.id.review);
            date = (TextView) itemView.findViewById(R.id.date);
            review_name = (TextView) itemView.findViewById(R.id.review_name);
            rating = (RatingBar) itemView.findViewById(R.id.rating);

            float radius = activity.getResources().getDimension(R.dimen._20sdp);
            review_profile.setShapeAppearanceModel(review_profile.getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, radius)
                    .build());
        }
    }
}
