package app.com.example.heeyoung.artsshow.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.com.example.heeyoung.artsshow.data.ArtsContract.BrandEntry;
import app.com.example.heeyoung.artsshow.data.ArtsContract.ImageEntry;
import app.com.example.heeyoung.artsshow.data.ArtsContract.ProductEntry;

/**
 * Created by heeyoung on 2015-01-24.
 */


public class ArtsDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "arts.db";

    public ArtsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_BRAND_TABLE = "CREATE TABLE" + BrandEntry.TABLE_NAME + "(" +
                BrandEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BrandEntry.COLUMN_BRAND_KEY + " INTEGER NOT NULL, " +
                BrandEntry.COLUMN_BRAND_TITLE + "TEXT NOT NULL, " +
                BrandEntry.COLUMN_BRAND_COUNTRY + "TEXT NOT NULL);";

        final String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE" + ProductEntry.TABLE_NAME + "(" +
                ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductEntry.COLUMN_PRD_KEY + " INTEGER NOT NULL, " +
                ProductEntry.COLUMN_PRD_TITLE + "TEXT NOT NULL, " +
                ProductEntry.COLUMN_PRD_DESC + "TEXT NOT NULL);";

        final String SQL_CREATE_IMAGE_TABLE = "CREATE TABLE" + ImageEntry.TABLE_NAME + "(" +
                ImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ImageEntry.COLUMN_IMAGE_KEY + " INTEGER NOT NULL, " +
                ImageEntry.COLUMN_IMAGE_URL + "TEXT NOT NULL, " +
                ImageEntry.COLUMN_BRAND_KEY + "TEXT NOT NULL);";

//        sqLiteDatabase.execSQL(SQL_CREATE_BRAND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
