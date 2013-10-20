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
import android.util.Log;

/*
 *  This file is modeled on:
 *  http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 */

/**
 * Created by logan on 10/19/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.bigtheta.astronomypal/databases/";
    private static String DB_NAME = "amp_db";
    private SQLiteDatabase mDatabase = null;
    private final Context mContext;

    public static final String[] tableUserColumns = {
            "_id",
            "name"
    };

    public static final String[] tableDocketColumns = {
            "_id",
            "name",
            "observations"
    };

    public static final String[] tableDocketItemColumns = {
            "_id",
            "docket_id",
            "stellar_object_id"
    };

    public static final String[] tableTelescopeColumns = {
            "_id",
            "name",
            "type",
            "manufacturer",
            "aperture_in"
    };

    public static final String[] tableSessionColumns = {
    };

    public static final String[] tableSessionItemColumns = {
            "_id",
            "session_id",
            "stellar_object_id",
            "time",
            "conditions",
            "description",
            "zipcode",
            "gps_latitude",
            "gps_longitude"
    };

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        SQLiteDatabase dbRead = null;
        if (dbExist) {
        } else {
            dbRead = this.getReadableDatabase();
            dbRead.close();
            try {
                this.close();
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
        openDatabase();
        close();
        openDatabase();
    }

    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e){
        }

        if(checkDB != null){
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
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
        this.close();
    }

    public void openDatabase() throws SQLiteException{
        String myPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(mDatabase != null) {
            mDatabase.close();
        }

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
        return mDatabase;
    }

    public int getUserId(String user) {
        Cursor cursor = getDatabase().query(
                "user", tableUserColumns, "user = ?",
                new String[] {user}, null, null, null);
        cursor.moveToFirst();
        int retval = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        cursor.close();
        return retval;
    }
}
