package com.example.ZohoContactApp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText mNameEditText, mNumberEditText, mEmailEditText;
    private Uri mCurrentContactUri;
    private boolean mContactHasChanged = false;
    public static final int LOADER = 0;

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mContactHasChanged = true;
            return false;
        }
    };

    boolean hasAllRequiredValues = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentContactUri = intent.getData();

        mNameEditText = findViewById(R.id.nameEditText);
        mNumberEditText = findViewById(R.id.phoneEditText);
        mEmailEditText = findViewById(R.id.emailEditText);
        mNameEditText.setOnTouchListener(mOnTouchListener);
        mNumberEditText.setOnTouchListener(mOnTouchListener);
        mEmailEditText.setOnTouchListener(mOnTouchListener);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menueditor, menu);
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentContactUri == null) {
            MenuItem item = (MenuItem) menu.findItem(R.id.delete);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                saveContact();
                if (hasAllRequiredValues == true) {
                    finish();
                }
                return true;

            case R.id.delete:
                showDeleteConfirmationDialog();
                return true;

            case android.R.id.home:
                if (!mContactHasChanged) {

                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;

                }

                DialogInterface.OnClickListener discardButton = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);

                    }
                };




        }
            return super.onOptionsItemSelected(item);
    }

    private boolean saveContact() {



        String name = mNameEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String phone = mNumberEditText.getText().toString().trim();


        if (mCurrentContactUri == null && TextUtils.isEmpty(name)
        && TextUtils.isEmpty(email) && TextUtils.isEmpty(phone)) {

            hasAllRequiredValues = true;
            return hasAllRequiredValues;

        }

        ContentValues values = new ContentValues();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name is Required", Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;


        } else {
            values.put(Contract.ContactEntry.COLUMN_NAME, name);
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is Required", Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;


        } else {
            values.put(Contract.ContactEntry.COLUMN_EMAIL, email);
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Phone Number is Required", Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;


        } else {
            values.put(Contract.ContactEntry.COLUMN_PHONENUMBER, phone);
        }



        if (mCurrentContactUri == null) {

            Uri newUri = getContentResolver().insert(Contract.ContactEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, "Error with Saving", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Success with Saving", Toast.LENGTH_SHORT).show();

            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentContactUri, values, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(this, "Error  with Update", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Success  with Update", Toast.LENGTH_SHORT).show();

            }

        }

        hasAllRequiredValues = true;

        return hasAllRequiredValues;




    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

       String[] projection = {Contract.ContactEntry._ID,
               Contract.ContactEntry.COLUMN_NAME,
               Contract.ContactEntry.COLUMN_EMAIL,
               Contract.ContactEntry.COLUMN_PHONENUMBER,

       };

       return new CursorLoader(this, mCurrentContactUri,
               projection, null,
               null,
               null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            // getting position of each column
            int name = cursor.getColumnIndex(Contract.ContactEntry.COLUMN_NAME);
            int email = cursor.getColumnIndex(Contract.ContactEntry.COLUMN_EMAIL);
            int number = cursor.getColumnIndex(Contract.ContactEntry.COLUMN_PHONENUMBER);

            String contactname = cursor.getString(name);
            String contactemail = cursor.getString(email);
            String contactnumber = cursor.getString(number);


            mNumberEditText.setText(contactnumber);
            mNameEditText.setText(contactname);
            mEmailEditText.setText(contactemail);


        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mNumberEditText.setText("");
        mNameEditText.setText("");
        mEmailEditText.setText("");

    }

    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the dele of the on in the database.
     */
    private void deleteProduct() {
        if (mCurrentContactUri != null) {

            int rowsDeleted = getContentResolver().delete(mCurrentContactUri, null, null);

            if (rowsDeleted == 0) {

                Toast.makeText(this, getString(R.string.delete_contact_failed),
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, getString(R.string.delete_contact_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }


        finish();
    }

    @Override
    public void onBackPressed() {
        if (!mContactHasChanged) {
            super.onBackPressed();
            return;
        }


    }
}

