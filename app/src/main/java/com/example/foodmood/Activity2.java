package com.example.foodmood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public  class Activity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Button yes,no;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    protected BottomNavigationView navigationView;
    CardView cv_chinese;
    TextView userName;
    String user ;
Intent i;
    Button it,ch,sf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        userName  =findViewById(R.id.username);
        i = new Intent(this,user_selected_categories.class);
        user = getIntent().getStringExtra("USERNAME");
        //user = "z";
        i.putExtra("USERNAME",user);
        Log.d("user",user);
        userName.setText("Hello , "+user);
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        builder = new AlertDialog.Builder(this);
    }
    public void desi(View view){
        i.putExtra("cat","Desi Food");
        Toast.makeText(this, "Desi selected", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
    public void sea(View view){
        i.putExtra("cat","Sea Food");
        Toast.makeText(this, "Sea selected", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
    public void italian(View view){
        i.putExtra("cat","Italian Food");
        Toast.makeText(this, "Sea selected", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
    public void chinese(View view){
       i.putExtra("cat","Chinese Food");
       Toast.makeText(this, "Chinese selected", Toast.LENGTH_SHORT).show();
       startActivity(i);
}
    public void dessert(View view){
        i.putExtra("cat","Dessert Food");
        Toast.makeText(this, "Dessert selected", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
    public void all(View view){
        i.putExtra("cat","Fast Food");
        Toast.makeText(this, "Fast Food selected", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.action_home) {
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Activity2.class));
            } else if (itemId == R.id.all_categories) {
                Toast.makeText(this, "All categories", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, user_selected_categories.class).putExtra("cat","All"));
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
                                Intent i = new Intent(Activity2.this,signin.class);
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
