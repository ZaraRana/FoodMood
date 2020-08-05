package com.example.foodmood;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyAdapterAdmin extends RecyclerView.Adapter<MyAdapterAdmin.RecipeViewHolder> {



    private Context mCtx;
    AlertDialog.Builder builder;
    EditText et_name,et_email,et_update_name,et_update_ingredients,et_update_recipe;
    Button add,btn_update,btn_cancel;
    AlertDialog dialog;
    private List<Recipe> productList;
    DbHelper helper;
    public MyAdapterAdmin(Context mCtx, List<Recipe> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.helper = new DbHelper(mCtx);
    }


    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recipe_design_admin,null);
        return new RecipeViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
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
                Intent i =new Intent(mCtx,SingleRecipe.class);
                int a = product.getId();
                i.putExtra("ID",a);
                i.putExtra("USERNAME","Admin");
                mCtx.startActivity(i);
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Update Info");
                builder.setCancelable(false);
                View view = LayoutInflater.from(mCtx).inflate(R.layout.dialog_edit,null,false);
                InitUpdateDialog(position,view,product);
                builder.setView(view);
                dialog = builder.create();
                dialog.show();

            }
        });

     holder.btn_delete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             removeItem(position);
             int a = product.getId();
             helper.delete(a);
             Toast.makeText(mCtx, "RECIPE DELETED WITH"+a, Toast.LENGTH_SHORT).show();
             String b = product.getName();
             Log.d("id","id "+a+" Name "+b);

         }

     });

    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice, textViewID;
        ImageView imageView;
        Button btn_delete, btn_edit;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.title_admin);
            imageView = itemView.findViewById(R.id.imageView_admin);
            btn_delete = itemView.findViewById(R.id.d1);
            btn_edit = itemView.findViewById(R.id.e1);

        }
    }

    private void InitUpdateDialog(final int position, View view, final Recipe recipe) {

        et_update_name = view.findViewById(R.id.n1);
        et_update_ingredients = view.findViewById(R.id.e2);
        et_update_recipe = view.findViewById(R.id.r1);

        btn_update = view.findViewById(R.id.btn_update_user);
        btn_cancel = view.findViewById(R.id.btn_update_cancel);

        et_update_name.setText(recipe.getName());
        et_update_ingredients.setText(recipe.getIngredients());
        et_update_recipe.setText(recipe.getDescription());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,ingredients,recipe_description;
                name = et_update_name.getText().toString();
                ingredients = et_update_ingredients.getText().toString();
                recipe_description = et_update_recipe.getText().toString();

                // Log.d("data",name+"---"+ingredients+"---"+recipe_description);
                Log.d("id"," "+recipe.getId());

                helper.updateRecipe(recipe.getId(),name, ingredients, recipe_description);
                dialog.dismiss();
                Recipe r = new Recipe(recipe.getId(),name,ingredients,recipe_description,recipe.getCategory(),recipe.getImage());
                UpdateData(position,r);
              //  Toast.makeText(MainActivity.this,"User Updated..",Toast.LENGTH_SHORT).show();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void UpdateData(int position,Recipe recipe){

        productList.remove(position);
        productList.add(position,recipe);
        notifyItemChanged(position);
        notifyDataSetChanged();
    }


    public void removeItem(int position){
        productList.remove(position);
        notifyDataSetChanged();

    }

    public void filterList(ArrayList<Recipe> filterdNames) {

        this.productList = filterdNames;
        notifyDataSetChanged();
    }

}
