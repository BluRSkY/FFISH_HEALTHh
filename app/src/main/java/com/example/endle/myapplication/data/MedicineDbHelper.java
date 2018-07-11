package com.example.endle.myapplication.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.endle.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.endle.myapplication.data.MedicineContract.MedicineEntry.CART_TABLE;
/**
 * Created by delaroy on 9/3/17.
 */

public class MedicineDbHelper extends SQLiteOpenHelper {
    private static final String TAG = MedicineDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "medicinescough.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;



    //Used to read data from res/ and assets/
    private Resources mResources;



    public MedicineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
        mContentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FRAGRANCE_TABLE = "CREATE TABLE " + MedicineContract.MedicineEntry.TABLE_NAME + " (" +
                MedicineContract.MedicineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedicineContract.MedicineEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                MedicineContract.MedicineEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                MedicineContract.MedicineEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                MedicineContract.MedicineEntry.COLUMN_PRICE + " REAL NOT NULL, " +
                MedicineContract.MedicineEntry.COLUMN_USERRATING + " INTEGER NOT NULL " + " );";

        final String SQL_CREATE_CART_FRAGRANCE_TABLE = "CREATE TABLE " + MedicineContract.MedicineEntry.CART_TABLE + " (" +
                MedicineContract.MedicineEntry._CARTID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedicineContract.MedicineEntry.COLUMN_CART_NAME + " TEXT UNIQUE NOT NULL, " +
                MedicineContract.MedicineEntry.COLUMN_CART_IMAGE + " TEXT NOT NULL, " +
                MedicineContract.MedicineEntry.COLUMN_CART_QUANTITY + " INTEGER NOT NULL, " +
                MedicineContract.MedicineEntry.COLUMN_CART_TOTAL_PRICE + " REAL NOT NULL " + " );";


        db.execSQL(SQL_CREATE_FRAGRANCE_TABLE);
        db.execSQL(SQL_CREATE_CART_FRAGRANCE_TABLE);
        Log.d(TAG, "Database Created Successfully" );


        try {
            readFragrancesFromResources(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
        db.execSQL("DROP TABLE IF EXISTS " + MedicineContract.MedicineEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MedicineContract.MedicineEntry.CART_TABLE);
        onCreate(db);
    }


    private void readFragrancesFromResources(SQLiteDatabase db) throws IOException, JSONException {
        //db = this.getWritableDatabase();
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.medicine);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();

        final String BGS_MEDICINES = "medicines";

        final String BGS_MEDICINENAME = "medicineName";
        final String BGS_DESCRIPTION = "description";
        final String BGS_IMAGEURL = "imageUrl";
        final String BGS_PRICE = "price";
        final String BGS_USERRATING = "userRating";

        try {
            JSONObject medicineJson = new JSONObject(rawJson);
            JSONArray medicineArray = medicineJson.getJSONArray(BGS_MEDICINES);


            for (int i = 0; i < medicineArray.length(); i++) {

                String medicineName;
                String description;
                String imageUrl;
                Double price;
                int userRating;

                JSONObject medicineDetails = medicineArray.getJSONObject(i);

                medicineName = medicineDetails.getString(BGS_MEDICINENAME);
                description = medicineDetails.getString(BGS_DESCRIPTION);
                imageUrl = medicineDetails.getString(BGS_IMAGEURL);
                price = medicineDetails.getDouble(BGS_PRICE);
                userRating = medicineDetails.getInt(BGS_USERRATING);


                ContentValues medicineValues = new ContentValues();

                medicineValues.put(MedicineContract.MedicineEntry.COLUMN_NAME, medicineName);
                medicineValues.put(MedicineContract.MedicineEntry.COLUMN_DESCRIPTION, description);
                medicineValues.put(MedicineContract.MedicineEntry.COLUMN_IMAGE, imageUrl);
                medicineValues.put(MedicineContract.MedicineEntry.COLUMN_PRICE, price);
                medicineValues.put(MedicineContract.MedicineEntry.COLUMN_USERRATING, userRating);

                 db.insert(MedicineContract.MedicineEntry.TABLE_NAME, null, medicineValues);

                Log.d(TAG, "Inserted Successfully " + medicineValues );
            }

            Log.d(TAG, "Inserted Successfully " + medicineArray.length() );

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }


}
