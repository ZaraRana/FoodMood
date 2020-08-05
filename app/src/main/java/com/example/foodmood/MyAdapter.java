package com.example.foodmood;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecipeViewHolder> {



    private Context mCtx;
    private List<Recipe> productList;
    String username;


    //this constructor is receiving arraylist from previous activity(user_selected_categories)
    //here name of arrayList is productList
    public MyAdapter(Context mCtx, List<Recipe> productList,String user) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.username = user;

    }


    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //recepe_design xml file is used as template of recipe
        View view = inflater.inflate(R.layout.recipe_design, null);
        return new RecipeViewHolder(view);
    }



    @Override //this methods calls itself number of times as the items in list given to this adapter
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
       // productList (previously known as arrayList) now contains the list items
        final Recipe product = productList.get(position); // getting single product item 1 by 1



        holder.textViewTitle.setText(product.getName());
        Log.d("URI",product.getImage()+"");

        if(product.getImage() != null) {
            Uri imageUri = Uri.parse(product.getImage());
            holder.imageView.setImageURI(imageUri);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Cardview selected: "+product.getId(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mCtx,SingleRecipe.class);
               // String a = user_selected_categories.getUser();
              //  Log.d("use in adap",a);
                i.putExtra("USERNAME",username);
                i.putExtra("ID",product.getId());
                mCtx.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageView;
        CardView cardView;


        public RecipeViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.card);

        }
    }



    public void filterListforAdapter(ArrayList<Recipe> filterdNames) {
        Log.d("class in MyAdapter"," "+getClass().getName());
        this.productList = filterdNames;
        notifyDataSetChanged();
    }

}
