package com.example.foodmood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    EditText username, password, cpass, email, age;
    Button signup;
    TextView signin1;
    SQLiteDatabase db;
    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        username = this.<EditText>findViewById(R.id.user);
        password = this.<EditText>findViewById(R.id.password);
        email = this.<EditText>findViewById(R.id.email);
        age = this.<EditText>findViewById(R.id.age);
        cpass = this.<EditText>findViewById(R.id.cpassword);
        signup = this.<Button>findViewById(R.id.signup);
        signin1 = this.<TextView>findViewById(R.id.signin1);
        Intent intent = getIntent();
        dbHelper = new DbHelper(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = username.getText().toString();
                String pass = password.getText().toString();
                String pass1 = cpass.getText().toString();
                String em = email.getText().toString();
                String ag = age.getText().toString();
                ContentValues values = new ContentValues();


                if ((TextUtils.isEmpty(email.getText())) && (TextUtils.isEmpty(password.getText())) && (TextUtils.isEmpty(cpass.getText())) && (TextUtils.isEmpty(username.getText())) && (TextUtils.isEmpty(age.getText()))) {
                    email.setError("Please fill the required field");
                    password.setError("Please fill the required field");
                    username.setError("please fill the requried field ");
                    cpass.setError("please fill the requried field ");
                    age.setError("please fill the requried field ");

                } else if ((TextUtils.isEmpty(email.getText()))) {
                    email.setError("Please fill the required field");
                } else if ((TextUtils.isEmpty(username.getText()))) {
                    username.setError("Please fill the required field");
                } else if ((TextUtils.isEmpty(cpass.getText()))) {
                    cpass.setError("Please fill the required field");
                } else if ((TextUtils.isEmpty(age.getText()))) {
                    age.setError("Please fill the required field");
                } else if (TextUtils.isEmpty(password.getText())) {
                    password.setError("Please fill the required field");
                } else {


                    if (pass.equalsIgnoreCase(pass1)) {
                        boolean inserted = dbHelper.insertData(username.getText().toString(),
                                email.getText().toString(), password.getText().toString(), age.getText().toString());
                        if (inserted == true) {
                            Toast.makeText(signup.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(signup.this,signin.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(signup.this, "Error while inserting data", Toast.LENGTH_LONG).show();
                            }


                    } else {
                        Toast.makeText(getApplicationContext(), "Password not same ", Toast.LENGTH_SHORT).show();
                    }


                }
            }


        });

        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intt = new Intent(signup.this, signin.class);
                startActivity(intt);

            }
        });

    }
}


