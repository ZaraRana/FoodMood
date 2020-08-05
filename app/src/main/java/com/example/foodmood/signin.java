package com.example.foodmood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signin extends AppCompatActivity {
    EditText Email, password , et_name,et_email,et_update_name,et_update_ingredients,et_update_recipe;
    Button signin,btn_update,btn_cancel;;
    TextView registered,forgot;
    SQLiteDatabase db;
    DbHelper dbHelper;
    AlertDialog.Builder builder;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        dbHelper = new DbHelper(this);
        final EditText Email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        TextView signup = (TextView) findViewById(R.id.registered);
        forgot = findViewById(R.id.forgot_password);
        Button btnLogin = (Button) findViewById(R.id.signin);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(signin.this);
                builder.setTitle("Update Password");
                builder.setCancelable(false);
                View view = LayoutInflater.from(signin.this).inflate(R.layout.dialog_update_password, null, false);
                InitUpdateDialog(view);
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((TextUtils.isEmpty(Email.getText())) && (TextUtils.isEmpty(password.getText()))) {
                    Email.setError("Please fill the required field");
                    password.setError("Please fill the required field");


                } else if ((TextUtils.isEmpty(Email.getText()))) {
                    Email.setError("Please fill the required field");
                } else if (TextUtils.isEmpty(password.getText())) {
                    password.setError("Please fill the required field");
                } else {

                    Boolean chkemailpass = dbHelper.validateUser(Email.getText().toString(), password.getText().toString());

                    if (chkemailpass) {
                        Toast.makeText(signin.this, "Signin Successfully", Toast.LENGTH_LONG).show();

                        Intent newint = new Intent(signin.this, Activity2.class);
                        String u = Email.getText().toString();
                        Log.d("user in signin", u);
                        newint.putExtra("USERNAME", Email.getText().toString());
                        startActivity(newint);

                    } else {
                        Toast.makeText(signin.this, "Wrong Email or Password", Toast.LENGTH_LONG).show();
                    }


                }
            }

        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intt = new Intent(signin.this, signup.class);
                startActivity(intt);

            }
        });

    }
        private void InitUpdateDialog(View view) {

            et_update_name = view.findViewById(R.id.name);
            et_update_ingredients = view.findViewById(R.id.password);
            et_update_recipe = view.findViewById(R.id.password_confirm);

            btn_update = view.findViewById(R.id.btn_update_user);
            btn_cancel = view.findViewById(R.id.btn_update_cancel);



            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name,ingredients,recipe_description;
                    name = et_update_name.getText().toString();
                    ingredients = et_update_ingredients.getText().toString();
                    recipe_description = et_update_recipe.getText().toString();

                    Log.d("data",name+"---"+ingredients+"---"+recipe_description);


                    dbHelper.updatePassword(name, ingredients, recipe_description);

                    dialog.dismiss();
                     Toast.makeText(signin.this,"Password Changed for username [ "+name+" ]",Toast.LENGTH_SHORT).show();
//                    Recipe r = new Recipe(recipe.getId(),name,ingredients,recipe_description,recipe.getCategory(),recipe.getImage());
//                    UpdateData(position,r);


                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

}



