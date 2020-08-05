package com.example.foodmood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
public class user_selected_categories extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {
    RecyclerView recyclerView;
    TextView tv;
    ArrayList<Recipe> arrayList = new ArrayList<>();
    DbHelper helper = new DbHelper(this);
static String user_name;
    MyAdapter adapter;
    EditText editTextSearch;
    protected BottomNavigationView navigationView;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selected_categories);

        user_name = getIntent().getStringExtra("USERNAME");

        recyclerView = findViewById(R.id.listView);
        editTextSearch=findViewById(R.id.editTextSearch);
        tv = findViewById(R.id.tv_title);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        loadData();  //getting gata from database

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        builder = new AlertDialog.Builder(this);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("listner_before", ""+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("listner onTextChanged",""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("listner after",s.toString());
                filter(s.toString());
            }
        });

    }

    public static String getUser(){
        return user_name;
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Recipe> filterdNames = new ArrayList<>();
            boolean found = false;
        //looping through existing elements
        for (Recipe s : arrayList) {
            //if the existing elements contains the search input

            if (s.getName().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                //adding the element to filtered list
                String a = s.getName().toLowerCase().trim();
                String b = text.toLowerCase().trim();
                filterdNames.add(s);
                // Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                Log.d("text",text);
                found =true;
            }
            else {
               // Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
                Log.d("t","not found");
            }
        }

        //calling a method of the adapter class and passing the filtered list
        Log.d("class in user sele"," "+getClass().getName());
        Log.d("class (name of adap)"," "+adapter.getClass().getSimpleName());
      adapter.filterListforAdapter(filterdNames);
    }


    public void loadData(){
        String i = getIntent().getStringExtra("cat");
        Log.d("cat",i);
        if(i.equals("All")){
            tv.setText("All Categories");
            Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();

            //getting all data from database and storing in arrayList
            arrayList = helper.getAllDataOfRecipes();

            //passing arrayList to adapter class
            Log.d("size",arrayList.size()+"");
            adapter  = new MyAdapter(this, arrayList,user_name);
            recyclerView.setAdapter(adapter);
        }
        else {
            tv.setText(i);
            Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
            arrayList = helper.getRecipebyCategory(i);
            adapter  = new MyAdapter(this, arrayList,user_name);
            recyclerView.setAdapter(adapter);
        }


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
                            Toast.makeText(getApplicationContext(),"Logout successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(user_selected_categories.this,signin.class);
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

}