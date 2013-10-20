package com.bigtheta.astronomypal;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by logan on 10/19/13.
 */
public class StellarObject {
    public static final String[] tableStellarObjectColumns = {
            "_id",
            "ngc_ic",
            "name",
            "description",
            "type",
            "distance",
            "size",
            "constellation",
            "magnitude",
            "right_ascension",
            "declination",
            "img_name"
    };

    public int mId;
    public String mNgcIc;
    public String mName;
    public String mDescription;
    public String mType;
    public double mDistance;
    public double mSize;
    public String mConstellation;
    public double mMagnitude;
    public String mRightAscension;
    public String mDeclination;
    public String mImageName;

    public StellarObject(SQLiteDatabase db, int _id) {
        mId = _id;
        if (db == null) {
            throw new Error("db is null");
        }
        Cursor cursor = db.query(
                "stellar_object", tableStellarObjectColumns, "_id = ?",
                new String[] {Long.toString(_id)}, null, null, null);
        cursor.moveToFirst();
        mNgcIc = cursor.getString(cursor.getColumnIndexOrThrow("ngc_ic"));
        mName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        mDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        mType = cursor.getString(cursor.getColumnIndexOrThrow("type"));
        mDistance = cursor.getDouble(cursor.getColumnIndexOrThrow("distance"));
        mSize = cursor.getDouble(cursor.getColumnIndexOrThrow("size"));
        mConstellation = cursor.getString(cursor.getColumnIndexOrThrow("constellation"));
        mMagnitude = cursor.getDouble(cursor.getColumnIndexOrThrow("magnitude"));
        mRightAscension = cursor.getString(cursor.getColumnIndexOrThrow("right_ascension"));
        mDeclination = cursor.getString(cursor.getColumnIndexOrThrow("declination"));
        mImageName = cursor.getString(cursor.getColumnIndexOrThrow("img_name"));
        cursor.close();
    }
}
