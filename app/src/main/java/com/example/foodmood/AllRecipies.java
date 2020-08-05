package com.example.foodmood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllRecipies extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapterAdmin adapter2;
    TextView tv;
    SearchView search;
    ArrayList<Recipe> arrayList = new ArrayList<>();
    DbHelper helper = new DbHelper(this);
    EditText editTextSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipies);
        recyclerView = findViewById(R.id.listView);
        tv = findViewById(R.id.tv_title);
        editTextSearch=findViewById(R.id.editTextSearch);
        int numberOfColumns = 2;


        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();  //getting gata from database

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
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Recipe> filterdNames = new ArrayList<>();

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
            }
            else {
                //Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
                Log.d("t","not found");
            }
        }
        //calling a method of the adapter class and passing the filtered list
                adapter2.filterList(filterdNames);
    }
    public void loadData(){


            arrayList = helper.getAllDataOfRecipes();
            Log.d("size in all recipies",arrayList.size()+"");
            adapter2 = new MyAdapterAdmin(this, arrayList);
            recyclerView.setAdapter(adapter2);




    }


   // @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        MenuItem item=menu.findItem(R.id.search);
//        androidx.appcompat.widget.SearchView
//        return super.onCreateOptionsMenu(menu);
//    }
}