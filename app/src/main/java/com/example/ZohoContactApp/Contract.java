package com.example.ZohoContactApp;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Contract {


    public Contract() {

    }

    public static final String CONTENT_AUTHORITY = "com.example.ZohoContactApp";
    public static final Uri BASE_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);
    public static final String PATH_CONTACTS = "mycontacts";

    public static abstract class ContactEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH_CONTACTS);

        public static final String TABLE_NAME = "mycontacts";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONENUMBER = "number";
        public static final String COLUMN_EMAIL = "email";


    }
}
