package com.example.ZohoContactApp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ZohoContactApp.Contract.ContactEntry;

public class Dbhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contact.db";

    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // THIS AUTOMATICALLY INCREMENTS THE ID BY 1
        String SQL_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL); ", ContactEntry.TABLE_NAME, ContactEntry._ID, ContactEntry.COLUMN_NAME, ContactEntry.COLUMN_EMAIL, ContactEntry.COLUMN_PHONENUMBER);
        db.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
