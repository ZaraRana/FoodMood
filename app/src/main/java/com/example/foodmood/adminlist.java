package com.example.foodmood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class adminlist extends AppCompatActivity {
Button btn_add,btn_view,a1,a2,a3,a4,a5,a6;
    DbHelper helper = new DbHelper(this);
    ArrayList<feedbackTemplate> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlist);

        init();
        loadData();


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminlist.this,addData.class);
                startActivity(i);
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminlist.this,AllRecipies.class);
                startActivity(i);
            }
        });
    }

    public void loadData(){
    arrayList =  helper.getStatistics();
        Toast.makeText(this, "Size: "+arrayList.size(), Toast.LENGTH_SHORT).show();
        try {


        a1.setText(arrayList.get(0).getCategory()+"  ("+arrayList.get(0).getCount()+")");
        a2.setText(arrayList.get(1).getCategory()+"  ("+arrayList.get(1).getCount()+")");
        a3.setText(arrayList.get(2).getCategory()+"  ("+arrayList.get(2).getCount()+")");
        a4.setText(arrayList.get(3).getCategory()+"  ("+arrayList.get(3).getCount()+")");
        a5.setText(arrayList.get(4).getCategory()+"  ("+arrayList.get(4).getCount()+")");
        a6.setText(arrayList.get(5).getCategory()+"  ("+arrayList.get(5).getCount()+")");
    }
        catch (Exception e){
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void init(){
        btn_add = findViewById(R.id.add);
        btn_view = findViewById(R.id.view);
        a1 = findViewById(R.id.btn_1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);
        a5 = findViewById(R.id.a5);
        a6 = findViewById(R.id.a6);

    }
}
