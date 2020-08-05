package com.example.foodmood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class loginactivity extends AppCompatActivity {
    EditText Email, password;
    Button login;
    SQLiteDatabase db;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        final EditText Email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((TextUtils.isEmpty(Email.getText())) && (TextUtils.isEmpty(password.getText()))) {
                    Email.setError("Please fill the required field");
                    password.setError("Please fill the required field");


                } else if ((TextUtils.isEmpty(Email.getText()))) {
                    Email.setError("Please fill the required field");
                } else if (TextUtils.isEmpty(password.getText())) {
                    password.setError("Please fill the required field");
                }
                cmail(Email.getText().toString() , password.getText().toString());

            }
        });
    }
    public void cmail(String email , String Password){
        if(email.equals("foodmood.com") && (Password.equals("1234"))){
            Toast.makeText(loginactivity.this, "Signin Successfully", Toast.LENGTH_LONG).show();
            Intent newint = new Intent(loginactivity.this, adminlist.class);
            startActivity(newint);

        }
        else {
            Toast.makeText(loginactivity.this, "Wrong Email or Password", Toast.LENGTH_LONG).show();
        }



    }



}
