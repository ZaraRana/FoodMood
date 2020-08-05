package com.example.foodmood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;

public class SingleRecipe extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
TextView title,ingredients,recipe,user_rating_tv,tv_link;
RatingBar ratingBar,ratingBar2;
int id;
String user,link_youtube;
EditText comment;
Button btn_sendfeedback;
ImageView featuredImage;
float user_rating = 0,avg_rating_from_db;
MyAdapterFeedback adapter;
    RecyclerView recyclerView;

    ArrayList<Recipe> arrayList = new ArrayList<>();
    ArrayList<feedbackTemplate> arrayList2 = new ArrayList<>();
    DbHelper helper = new DbHelper(this);

    protected BottomNavigationView navigationView;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
        init();
        loading_data();

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        builder = new AlertDialog.Builder(this);



       tv_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link_youtube));
                    startActivity(i);
                }
            });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               // Toast.makeText(SingleRecipe.this, rating+" Stars", Toast.LENGTH_SHORT).show();
                Log.d("rating on click", ""+ratingBar.getRating());

                ratingBar2.setRating(rating);
                user_rating = rating;
               // helper.insertRating(rating,id);
            }
        });

        btn_sendfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String c = comment.getText().toString();
              float u = user_rating;
              String d = user;
              Log.d("data in single","comment: "+c+" rating: "+ u+" user: "+d);
                helper.insertRating(user_rating,comment.getText().toString(),user,id);
                feedbackTemplate r = new feedbackTemplate(user,comment.getText().toString(),user_rating);
                UpdateData(r);
               comment.setText("");

               float new_rating = (user_rating+avg_rating_from_db)/2;
                Toast.makeText(SingleRecipe.this, "New rating: "+new_rating, Toast.LENGTH_SHORT).show();
                ratingBar2.setRating(new_rating);
                user_rating_tv.setText("Rating: "+new_rating+" stars");
            }
        });

    }


    public void init(){
        recyclerView = findViewById(R.id.listView);
        featuredImage = findViewById(R.id.imgView);
        title = findViewById(R.id.title);
        tv_link = findViewById(R.id.link);
       // tv_link.setMovementMethod(LinkMovementMethod.getInstance());
        ingredients = findViewById(R.id.ingredients);
        recipe = findViewById(R.id.recipe);
        comment = findViewById(R.id.commentbox);
        ratingBar = findViewById(R.id.rating);
        Log.d("rating on start", ""+ratingBar.getRating());
        ratingBar2 = findViewById(R.id.rating2);
        btn_sendfeedback = findViewById(R.id.btn_sendFeedback);
        user_rating_tv = findViewById(R.id.tv_user_rating);
        id = getIntent().getIntExtra("ID",1);
        //user = getIntent().getStringExtra("USERNAME");
        user ="Zara";
        if (user.equals("Admin")){
            comment.setVisibility(View.INVISIBLE);
            btn_sendfeedback.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
        }
        //user = "z";
        Toast.makeText(this, "user is :"+user, Toast.LENGTH_LONG).show();
    }

    public void loading_data(){
        arrayList =  helper.getRecipebyID(id);
        featuredImage.setImageURI(Uri.parse(arrayList.get(0).getImage()));
        title.setText(arrayList.get(0).getName());
        recipe.setText("RECEPES: \n"+arrayList.get(0).getDescription());
        ingredients.setText("INGREDIENTS: \n"+arrayList.get(0).getIngredients());
        //tv_link.setText(arrayList.get(0).getLink());
        link_youtube = arrayList.get(0).getLink();

        avg_rating_from_db =  helper.getRatingbyID(id);
        ratingBar2.setRating(avg_rating_from_db);

        user_rating_tv.setText("Rating: "+avg_rating_from_db+" stars");
        Toast.makeText(this, "Avg rating: "+avg_rating_from_db, Toast.LENGTH_SHORT).show();

        arrayList2 = helper.getAllFeedbackData(id);
        adapter  = new MyAdapterFeedback(this, arrayList2);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_home) {
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Activity2.class));
        } else if (itemId == R.id.all_categories) {
            Toast.makeText(this, "All categories", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, user_selected_categories.class).putExtra("cat","All Categories"));
        } else if (itemId == R.id.action_more) {
            // Toast.makeText(this, "ActionMore", Toast.LENGTH_SHORT).show();
            //Uncomment the below code to Set the message and title from the strings.xml file
            builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            builder.setMessage("Do you want to logout ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishActivity(0);
                            Toast.makeText(getApplicationContext(),"LogOut successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SingleRecipe.this,signin.class);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"not logout",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating
            // dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("AlertDialogExample");
            alert.show();
        }


        return true;
    }
    public void UpdateData(feedbackTemplate template){


        arrayList2.add(template);
        adapter.notifyDataSetChanged();
        //notifyDataSetChanged();
    }

}
