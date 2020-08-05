package com.example.foodmood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final String DATABASE_NAME = "DB";
    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + DbContractor.f2.users_TABLE + "("
            + DbContractor.f2.COLUMN_Name + " TEXT ,"   //COLUMN_Name = "name";
            + DbContractor.f2.COLUMN_Email + " TEXT PRIMARY KEY,"
            + DbContractor.f2.COLUMN_Password + " TEXT,"            //COLUMN_Password = "password";
            + DbContractor.f2.COLUMN_CPassword + " TEXT,"    //COLUMN_CPassword = "cpassword";
            + DbContractor.f2.Column_Age + " TEXT,"
            + DbContractor.f2.COLUMN_Role + " TEXT);";


    // Recipes table
    private static final String CREATE_TABLE_REP = "CREATE TABLE "
            + DbContractor.f3.REP_TABLE + " ("                                //REP_TABLE = "recipes";
            + DbContractor.f3.COL_ID + "  Integer PRIMARY KEY AUTOINCREMENT,"                     // COL_NAME = "name";
            + DbContractor.f3.COL_NAME + "  TEXT UNIQUE, "
            + DbContractor.f3.Col_ING + " TEXT,"
            + DbContractor.f3.Col_REP + " TEXT,"
            + DbContractor.f3.Col_CAT + " TEXT ,"                       //  Col_CAT = "category";
            + DbContractor.f3.Col_IMG + "  TEXT ,"
            + DbContractor.f3.Col_Link + "  TEXT ,"
            + DbContractor.f3.Col_rat + "  TEXT )";                             // Col_IMG = "image_url"


    String CREATE_TABLE_CATEGORIES = "Create Table IF NOT EXISTS Categories(_id INTEGER PRIMARY KEY AUTOINCREMENT,Category Text)";
    String CREATE_TABLE_Ratings = "Create Table IF NOT EXISTS Rating (_id INTEGER PRIMARY KEY AUTOINCREMENT, Rating Float,Comments TEXT ,username TEXT, Recipe_id INTEGER)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DbContractor.f2.DB_VERSION);
    }




    public void insertRecipe(String name, String ingredients,String recipe,String category,String img,String link){
        db =this.getWritableDatabase();

        ContentValues Recipies = new ContentValues();

        Recipies.put("name", name);
        Recipies.put("ingredients",ingredients);
        Recipies.put("recipe", recipe);
        Recipies.put("category", category);
        Recipies.put("image_url", img);
        Recipies.put("link", link);

        db.insert("recipes", null, Recipies);

        Log.d("insert","recipe values inserted");


    }


    public void insertRating(float rating,String comment, String user,int id){
        db =this.getWritableDatabase();

        ContentValues Recipies = new ContentValues();

        Recipies.put("Rating", rating);
        Recipies.put("Comments", comment);
        Recipies.put("username", user);
        Recipies.put("Recipe_id",id);
        Log.d("values","Rating "+rating+"  id:"+ id+" comment "+comment+"  user:"+ user);

        db.insert("Rating", null, Recipies);
        Log.d("insert","recipe values inserted");


    }

    public float getRatingbyID(int c)
    {

        db =this.getReadableDatabase();
        String query = "SELECT * FROM Rating WHERE Recipe_id = "+c;
        Cursor result = db.rawQuery(query,null);

        float sum = 0;
        float average = 0;
        if (result.moveToFirst()){
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext())
            {
                sum = sum + result.getFloat(1);
            }
                average = sum / result.getCount();
                Log.d("average",sum+" "+" avg:"+ average);

            }





        return average;
    }
    public void updatePassword(String name, String password,String confirm_password){
        db =this.getWritableDatabase();
        String q = "UPDATE "+ DbContractor.f2.users_TABLE +"\n" +
                "SET password = '"+password+"',cpassword = '"+confirm_password+"'\n"+
                "WHERE name='"+name+"'";


        Log.d("update_password",q);
        db.execSQL(q);



    }
    public void updateRecipe(int id,String name, String ingredients,String recipe){
        db =this.getWritableDatabase();
        String q = "UPDATE "+ DbContractor.f3.REP_TABLE +"\n" +
                   "SET name='"+name+ "', ingredients = '"+ingredients+"',recipe = '"+recipe+"'\n" +
                   "WHERE id="+id;


        Log.d("update",q);
        db.execSQL(q);



    }

    public Cursor getAllCategories()
    {
        db =this.getReadableDatabase();
        String query = "select * from Categories";
        Cursor result = db.rawQuery(query,null);
        Log.d("getAllCategories","result: "+result.getCount());

        return result;
    }



    public ArrayList<Recipe> getAllDataOfRecipes()
    {
        ArrayList<Recipe> list =new  ArrayList< >() ;
        db =this.getReadableDatabase();
        String query = "SELECT * FROM recipes";
        Cursor result = db.rawQuery(query,null);

        if (result.moveToFirst()){
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext())
            {
                int id = result.getInt(0);
                String name = result.getString(1);
                String ingredients=result.getString(2);
                String description = result.getString(3);
                String category = result.getString(4);
                String img_url = result.getString(5);
                Log.d("Data","ID: "+id+" Name: "+name+ " Ingredients :" +ingredients+" Description: "+description+" Category: "+category+" Img: "+img_url);
                Recipe r = new Recipe(id,name,ingredients,description,category,img_url);

                list.add(r);
            }
        }

        return list;
    }
    public ArrayList<feedbackTemplate> getAllFeedbackData(int id)
    {
        ArrayList<feedbackTemplate> list =new  ArrayList< >() ;
        db =this.getReadableDatabase();
        String query = "SELECT * FROM Rating WHERE Recipe_id = "+ id;
        Cursor result = db.rawQuery(query,null);

        if (result.moveToFirst()){
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext())
            {

                float rating = result.getFloat(1);
                String comment = result.getString(2);
                String username = result.getString(3);

               // Log.d("Data","ID: "+id+" Name: "+name+ " Ingredients :" +ingredients+" Description: "+description+" Category: "+category+" Img: "+img_url);
                feedbackTemplate r = new feedbackTemplate(username,comment,rating);

                list.add(r);
            }
        }

        return list;
    }

    public ArrayList<feedbackTemplate> getStatistics()
    {
        ArrayList<feedbackTemplate> list =new  ArrayList< >() ;
        db =this.getReadableDatabase();
        String query = "SELECT category, count(category) as count FROM recipes GROUP BY category";
        Cursor result = db.rawQuery(query,null);

        if (result.moveToFirst()){
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext())
            {


                String category = result.getString(0);
                int count = result.getInt(1);

                // Log.d("Data","ID: "+id+" Name: "+name+ " Ingredients :" +ingredients+" Description: "+description+" Category: "+category+" Img: "+img_url);
                feedbackTemplate r = new feedbackTemplate(category, count);

                list.add(r);
            }
        }

        return list;
    }


    public ArrayList<Recipe> getRecipebyID(int c)
    {
        ArrayList<Recipe> list =new  ArrayList< >() ;
        db =this.getReadableDatabase();
        String query = "SELECT * FROM recipes WHERE id = "+c;
        Cursor result = db.rawQuery(query,null);
        if (result.moveToFirst()){
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext())
            {
                int id = result.getInt(0);
                String name = result.getString(1);
                String ingredients=result.getString(2);
                String description = result.getString(3);
                String category = result.getString(4);
                String img_url = result.getString(5);
                String link = result.getString(6);
                Log.d("imgURI",""+img_url);
                Recipe r = new Recipe(id,name,ingredients,description,category,img_url,link);
                list.add(r);
            }
        }

        return list;
    }


    public ArrayList<Recipe> getUserNamebyID(int c)
    {
        ArrayList<Recipe> list =new  ArrayList< >() ;
        db =this.getReadableDatabase();
        String query = "SELECT * FROM recipes WHERE id = "+c;
        Cursor result = db.rawQuery(query,null);
        if (result.moveToFirst()){
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext())
            {
                int id = result.getInt(0);
                String name = result.getString(1);
                String ingredients=result.getString(2);
                String description = result.getString(3);
                String category = result.getString(4);
                String img_url = result.getString(5);
                Log.d("imgURI",""+img_url);
                Recipe r = new Recipe(id,name,ingredients,description,category,img_url);
                list.add(r);
            }
        }

        return list;
    }
    public ArrayList<Recipe> getRecipebyCategory(String c)
    {
        ArrayList<Recipe> list =new  ArrayList< >() ;
        db =this.getReadableDatabase();
        String query = "SELECT * FROM recipes WHERE category = '" + c + "'";
        Cursor result = db.rawQuery(query,null);
        if (result.moveToFirst()){
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext())
            {
                 int id = result.getInt(0);
                String name = result.getString(1);
                String ingredients=result.getString(2);
                String description = result.getString(3);
                String category = result.getString(4);
                String img_url = result.getString(5);

                Recipe r = new Recipe(id,name,ingredients,description,category,img_url);
                list.add(r);
            }
        }

        return list;
    }
    public void delete(int a)
    {

        db =this.getWritableDatabase();
        String query = "DELETE FROM recipes WHERE id = "+a;
        db.execSQL(query);
    }





    public boolean cemail(String email){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from user_TABLE  where email =? " , new String[]{email});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean validateUser(String name, String pass)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DbContractor.f2.COLUMN_Name + " = ?" + " AND " +DbContractor.f2.COLUMN_Password + " = ?";
        String[] where= {name,pass};
        String[] columns = {DbContractor.f2.COLUMN_Email,DbContractor.f2.COLUMN_Password
        };
        Cursor cursor = db.query(DbContractor.f2.users_TABLE, columns,selection,where,null,null,null);
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public boolean insertData(String name,String email, String pass, String age)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContractor.f2.COLUMN_Name, name);
        contentValues.put(DbContractor.f2.COLUMN_Email,email);
        contentValues.put(DbContractor.f2.COLUMN_Password, pass);
        contentValues.put(DbContractor.f2.Column_Age,age);
        long result = db.insert(DbContractor.f2.users_TABLE, null, contentValues);
        if (result == -1)
        {
            return false;

        }

        else
        {
            return true;
        }

    }

    public boolean addData(String name,String rep)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContractor.f3.COL_NAME, name);
        contentValues.put(DbContractor.f3.Col_REP,rep);
        long result = db.insert(DbContractor.f3.REP_TABLE, null, contentValues);
        if (result == -1)
        {
            return false;

        }

        else
        {
            return true;
        }

    }
    public boolean emailpassword(String email, String password) {
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor=db.rawQuery("select * from user_TABLE  where email =? and password=? " , new String[]{email , password});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkmail(String email, String pass)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DbContractor.f2.COLUMN_Email + " = foodmood.com" + " AND " +DbContractor.f2.COLUMN_Password + " = 1234";
        String[] where= {email,pass};
        String[] columns = {DbContractor.f2.COLUMN_Email,DbContractor.f2.COLUMN_Password
        };
        Cursor cursor = db.query(DbContractor.f2.users_TABLE, columns,selection,where,null,null,null);
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_REP);
        db.execSQL(CREATE_TABLE_Ratings);
        Log.d("RecipeTable",""+CREATE_TABLE_REP);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        String q = "insert into Categories VALUES(null,'Chinese Food')";
        db.execSQL(q);
        q = "insert into Categories VALUES(null,'Italian Food')";
        db.execSQL(q);
        q = "insert into Categories VALUES(null,'Sea Food')";
        db.execSQL(q);
        q = "insert into Categories VALUES(null,'Desi Food')";
        db.execSQL(q);
        q = "insert into Categories VALUES(null,'Dessert Food')";
        db.execSQL(q);
        q = "insert into Categories VALUES(null,'Fast Food')";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContractor.f2.users_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbContractor.f3.REP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS Rating");
        db.execSQL("DROP TABLE IF EXISTS Categories");
        onCreate(db);

    }

}