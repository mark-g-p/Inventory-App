package com.example.android.inventoryappstage1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.inventoryappstage1.data.BooksContract.BookEntry;
import com.example.android.inventoryappstage1.data.BooksContract.SupplierEntry;
import com.example.android.inventoryappstage1.data.BooksDbHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = BooksDbHelper.class.getSimpleName();
    private BooksDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBook();
                insertSupplier();
                displayData(queryData());
            }
        });
        dbHelper = new BooksDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData(queryData());
    }

    // Insert dummy data for book
    private long insertBook() {
        String name = "T235oo";
        int price = 110;
        int quantity = 323;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, name);
        values.put(BookEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(BookEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(BookEntry._ID_SUPPLIER, 1);

        Log.e(LOG_TAG, String.valueOf(values));
        // Insert the new row
        return db.insert(BookEntry.TABLE_NAME, null, values);
    }

    // Insert dummy data for supplier.
    private long insertSupplier() {
        String name = "Penguin";
        String phoneNumber = "123351245";

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SupplierEntry.COLUMN_SUPPLIER_NAME, name);
        values.put(SupplierEntry.COLUMN_SUPPLIER_PHONE_NUMBER, phoneNumber);

        Log.e(LOG_TAG, String.valueOf(values));
        // Insert the new row
        return db.insert(SupplierEntry.TABLE_NAME, null, values);
    }

    private Cursor queryData() {
        String[] projection = {
                BookEntry.TABLE_NAME + "." + BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRODUCT_PRICE,
                BookEntry.COLUMN_PRODUCT_QUANTITY,
                SupplierEntry.COLUMN_SUPPLIER_NAME,
                SupplierEntry.COLUMN_SUPPLIER_PHONE_NUMBER
        };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(String.format(Locale.US, "%s LEFT OUTER JOIN %s ON %s.%s=%s.%s",
                BookEntry.TABLE_NAME, SupplierEntry.TABLE_NAME, SupplierEntry.TABLE_NAME, SupplierEntry._ID, BookEntry.TABLE_NAME, BookEntry._ID),
                projection,
                null,
                null,
                null,
                null,
                null);
    }

    private void displayData(Cursor cursor) {
        TextView displayView = findViewById(R.id.textView);
        try {
            String headerRow = String.format(Locale.getDefault(),
                    "\n %s - %s - %s - %s - %s - %s",
                    BookEntry._ID,
                    BookEntry.COLUMN_PRODUCT_NAME,
                    BookEntry.COLUMN_PRODUCT_PRICE,
                    BookEntry.COLUMN_PRODUCT_QUANTITY,
                    SupplierEntry.COLUMN_SUPPLIER_NAME,
                    SupplierEntry.COLUMN_SUPPLIER_PHONE_NUMBER);
            Log.e(LOG_TAG, headerRow);
            displayView.append(headerRow);

            int bookIdColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(SupplierEntry.COLUMN_SUPPLIER_NAME);
            int phoneColumnIndex = cursor.getColumnIndex(SupplierEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            while (cursor.moveToNext()) {
                String row = String.format(Locale.US,
                        "\n %s - %s - %s -  %s - %s - %s",
                        cursor.getInt(bookIdColumnIndex),
                        cursor.getString(nameColumnIndex),
                        cursor.getInt(priceColumnIndex),
                        cursor.getInt(quantityColumnIndex),
                        cursor.getString(supplierNameColumnIndex),
                        cursor.getString(phoneColumnIndex)
                );
                // Display the values from each column of the current row in the cursor in the TextView
                Log.e(LOG_TAG, row);
                displayView.append(row);
            }
        } finally {
            cursor.close();
        }
    }
}
