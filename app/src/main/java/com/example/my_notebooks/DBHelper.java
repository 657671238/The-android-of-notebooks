package com.example.my_notebooks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{


    DBHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version); //必须的
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users("
                +"id INTEGER PRIMARY KEY,"
                +"password VARCHAR(20))");
        db.execSQL("CREATE TABLE IF NOT EXISTS subjects("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"number INTEGER ,"
                +"subject VARCHAR(20))");
        db.execSQL("CREATE TABLE IF NOT EXISTS deatails("
                +"id INTEGER ,"
                +"subject VARCHAR(20),"
                +"title VARCHAR(20),"
                +"text TEXT,"
                +"time TEXT,"
                +"order_text TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS subjects");

        onCreate(db);

    }
}
