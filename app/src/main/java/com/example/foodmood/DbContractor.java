package com.example.foodmood;

import android.provider.BaseColumns;

public class DbContractor {
    public static abstract class f2 implements BaseColumns {
        public static final String users_TABLE = "users";
        public static final String COLUMN_Name = "name";
        public static final String COLUMN_Password = "password";
        public static final String COLUMN_CPassword = "cpassword";
        public static final String COLUMN_Email = "email";
        public static final String Column_Age = "sge";
        public static final String COLUMN_Role = "role";
        public static final int DB_VERSION = 2;


    }

    public static abstract class f3 implements BaseColumns {
        public static final String REP_TABLE = "recipes";
        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String Col_ING = "ingredients";
        public static final String Col_REP = "recipe";

        public static final String Col_CAT = "category";
        public static final String Col_IMG = "image_url";
        public static final String Col_Link = "link";
        public static final String Col_rat = "rating";
    }
}