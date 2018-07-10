package com.example.android.inventoryappstage1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.inventoryappstage1.data.BooksContract.BookEntry;
import com.example.android.inventoryappstage1.data.BooksContract.SupplierEntry;

public class BooksDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = BooksDbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "books.db";

    public BooksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        Create table of suppliers
        String SQL_CREATE_SUPPLIERS_TABLE = String.format("CREATE TABLE %s(" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s TEXT);",
                SupplierEntry.TABLE_NAME,
                SupplierEntry._ID,
                SupplierEntry.COLUMN_SUPPLIER_NAME,
                SupplierEntry.COLUMN_SUPPLIER_PHONE_NUMBER);
        sqLiteDatabase.execSQL(SQL_CREATE_SUPPLIERS_TABLE);
        Log.e(LOG_TAG, SQL_CREATE_SUPPLIERS_TABLE);

//        Create table of books
        String SQL_CREATE_BOOKS_TABLE = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, %s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL DEFAULT 0, " +
                        "%s INTEGER, " +
                        "FOREIGN KEY(%s) REFERENCES %s(%s));",
                BookEntry.TABLE_NAME,
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRODUCT_PRICE,
                BookEntry.COLUMN_PRODUCT_QUANTITY,
                BookEntry._ID_SUPPLIER,
                BookEntry._ID, SupplierEntry.TABLE_NAME, SupplierEntry._ID);
        sqLiteDatabase.execSQL(SQL_CREATE_BOOKS_TABLE);
        Log.e(LOG_TAG, SQL_CREATE_BOOKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
  // The database is still at version 1, so there's nothing to do be done here.
    }

}
