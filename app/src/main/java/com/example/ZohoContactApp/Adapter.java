package com.example.ZohoContactApp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class Adapter extends CursorAdapter {


    public Adapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listitem, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView, numberView, emailView, typeofContactView;

        nameView = view.findViewById(R.id.textName);
        emailView = view.findViewById(R.id.textEmail);
        numberView = view.findViewById(R.id.textNumber);
        int name = cursor.getColumnIndex(Contract.ContactEntry.COLUMN_NAME);
        int email = cursor.getColumnIndex(Contract.ContactEntry.COLUMN_EMAIL);
        int number = cursor.getColumnIndex(Contract.ContactEntry.COLUMN_PHONENUMBER);

        String contactname = cursor.getString(name);
        String contactemail = cursor.getString(email);
        String contactnumber = cursor.getString(number);


        nameView.setText(contactname);
        numberView.setText(contactnumber);
        emailView.setText(contactemail);



    }
}
