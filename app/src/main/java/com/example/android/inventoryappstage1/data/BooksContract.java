package com.example.android.inventoryappstage1.data;

import android.provider.BaseColumns;

public class BooksContract {
    //  Prevent user from creating object BooksContract.
    private BooksContract() {
    }


    public static final class SupplierEntry implements BaseColumns {
        public static final String TABLE_NAME = "suppliers";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

    }

    public static final class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String _ID_SUPPLIER = "supplier_id";
    }
}
