package com.bigtheta.astronomypal;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.io.IOException;

/*
 *  This file is modeled on:
 *  http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 */

/**
 * Created by logan on 10/19/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com/bigtheta/astronomypal/databases/";
    private static String DB_NAME = "amp.db";
    private SQLiteDatabase mDatabase = null;
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(
                    myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDatabase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase() throws SQLiteException{
        String myPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {

        if(mDatabase != null)
            mDatabase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void initializeDatabase() {

    }

    public SQLiteDatabase getDatabase() {
        if (mDatabase == null) {
            openDatabase();
        }
        return mDatabase;
    }

    private static String[] tableUserColumns = {
        "_id",
        "name"
    };

    public String fooQuery() {
        Cursor cursor = getDatabase().query(
                "user", tableUserColumns, "name = ?",
                new String[] {"John Smith"}, null, null, null);
        cursor.moveToFirst();
        String retval = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        cursor.close();
        return retval;
    }


// Add your public helper methods to access and get content from the database.
// You could return cursors by doing "return mDatabase.query(....)" so it'd be easy
// to you to create adapters for your views.

}
