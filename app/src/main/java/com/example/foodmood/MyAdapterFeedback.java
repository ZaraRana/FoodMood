package com.example.foodmood;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterFeedback extends RecyclerView.Adapter<MyAdapterFeedback.RecipeViewHolder> {



    private Context mCtx;
    private List<feedbackTemplate> productList;

    public MyAdapterFeedback(Context mCtx, List<feedbackTemplate> productList) {
        this.mCtx = mCtx;
        this.productList = productList;

    }


    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.feedback, null);
        return new RecipeViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        final feedbackTemplate product = productList.get(position); // getting single product item 1 by 1



        holder.userName.setText(product.getUser());
       // Log.d("URI",product.getImage()+"");

        holder.comment.setText(product.getComment());
        holder.rating.setRating(product.getRating());




    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView userName,comment;
        RatingBar rating;



        public RecipeViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user);
            comment = itemView.findViewById(R.id.comment);
            rating = itemView.findViewById(R.id.ratings);

        }
    }





}
