package com.example.endle.myapplication.data;

import android.database.Cursor;

/**
 * Created by delaroy on 9/3/17.
 */
public class Medicine {

    public int id;

    public String name;
    public String description;
    public String imageUrl;
    public Double price;
    public int userRating;


    public Medicine(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex(MedicineContract.MedicineEntry.COLUMN_NAME));
        this.description = cursor.getString(cursor.getColumnIndex(MedicineContract.MedicineEntry.COLUMN_DESCRIPTION));
        this.imageUrl = cursor.getString(cursor.getColumnIndex(MedicineContract.MedicineEntry.COLUMN_IMAGE));
        this.price = cursor.getDouble(cursor.getColumnIndex(MedicineContract.MedicineEntry.COLUMN_PRICE));
        this.userRating = cursor.getInt(cursor.getColumnIndex(MedicineContract.MedicineEntry.COLUMN_USERRATING));
    }

}
