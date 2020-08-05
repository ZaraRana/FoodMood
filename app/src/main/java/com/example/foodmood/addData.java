package com.example.foodmood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class addData extends AppCompatActivity {
    EditText et_foodName, et_foodRecipe,et_ing,et_link;
    Button btn_add;
    DbHelper dbHelper;
    ImageView btn_image;
    Spinner spinner;
    Cursor result;
    String category,picturePath;
    private static int RESULT_LOAD_IMG = 1;
    public final int REQUEST_CODE_FOR_PERMISSIONS = 654;
    ArrayList<String> spinnerItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);


        et_foodName = findViewById(R.id.n1);
        et_ing=findViewById(R.id.e2);
        et_foodRecipe = (EditText) findViewById(R.id.r1);
        et_link = findViewById(R.id.link);
        btn_image= findViewById(R.id.pick_image);


        btn_add = (Button) findViewById(R.id.add);
        btn_add.setEnabled(false);
        dbHelper = new DbHelper(this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_foodName.getText().toString();
                String ing=et_ing.getText().toString();
                String rep = et_foodRecipe.getText().toString();
                String link = et_link.getText().toString();

                if ((TextUtils.isEmpty(et_foodName.getText())) && (TextUtils.isEmpty(et_ing.getText())) && (TextUtils.isEmpty(et_foodRecipe.getText()))) {
                    et_foodName.setError("Please fill the required field");

                } else if ((TextUtils.isEmpty(et_foodName.getText()))) {
                    et_foodName.setError("Please fill the required field");
                } else if ((TextUtils.isEmpty(et_foodRecipe.getText()))) {
                    et_foodRecipe.setError("Please fill the required field");
                }
                else if ((TextUtils.isEmpty(et_ing.getText()))) {
                    et_ing.setError("Please fill the required field");
                }
              //  try{
                    dbHelper.insertRecipe(name,ing,rep,category,picturePath,link);
                    Toast.makeText(addData.this, "Recipe added successfully: ", Toast.LENGTH_SHORT).show();

//                }
//                catch(Exception e){
//                    Toast.makeText(addData.this, "This Recipe already exists: "+picturePath, Toast.LENGTH_SHORT).show();
//
//                }


                et_foodName.setText("");
                et_foodRecipe.setText("");
                et_ing.setText("");
                et_link.setText("");
                btn_image.setImageResource(R.drawable.imagee);


            }
        });

        fillSpinner();
        Log.d("Spinner", "After fillSpinner()");

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerItems) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.RED);
                } else {
                    tv.setTextColor(Color.BLUE);
                }
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_border);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String selectedItemText = (String) adapterView.getItemAtPosition(position);
                if (position > 0) {
                    btn_add.setEnabled(true);
                    category = selectedItemText;
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMG);


            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOR_PERMISSIONS);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMG){
            Uri selectedImage = data.getData();

            picturePath = selectedImage.toString();
            btn_image.setImageURI(selectedImage);

            Log.i("uri.", ""+selectedImage);
            //getImage(bm);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_FOR_PERMISSIONS){
            //You need to handle permission results, if user didn't allow them.
        }
    }

    public void getImage(Bitmap bm) {

        Log.i("Hi..", "In getImage "+bm);
        //int id = (int) dbHelper.insertBitmap(bm);
        //imageView.setImageBitmap(dbh.getBitmap(id));
    }
    public void fillSpinner() {
        Log.d("Spinner", "Inside fillSpinner()");
        result = dbHelper.getAllCategories();
        spinnerItems.add("Select Category");
        while (result.moveToNext()) {
            spinnerItems.add(result.getString(1));

        }
    }
}



