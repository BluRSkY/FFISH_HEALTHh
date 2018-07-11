package com.example.endle.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.endle.myapplication.Fragment.MapsActivity;
import com.example.endle.myapplication.R;
import com.example.endle.myapplication.count.Utils;
import com.example.endle.myapplication.data.Medicine;
import com.example.endle.myapplication.data.MedicineContract;
import com.example.endle.myapplication.data.MedicineDbHelper;
import com.joanzapata.iconify.widget.IconButton;

import static com.example.endle.myapplication.R.id.bottom_navigation;
import static com.example.endle.myapplication.R.id.cart_badge;
import static com.example.endle.myapplication.data.MedicineContract.MedicineEntry.CART_TABLE;

public class Medicine_Cough extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    MedicineAdapter fragranceAdapter;
    private static final int FRAGRANCE_LOADER = 0;
    MedicineDbHelper fragranceDbHelper;
    private SQLiteDatabase mDb;

    private int mNotificationsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine__cough);
        MedicineDbHelper dbHelper = new MedicineDbHelper(this);
        mDb = dbHelper.getWritableDatabase();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        fragranceAdapter = new MedicineAdapter(this, null);
        recyclerView.setAdapter(fragranceAdapter);

        getLoaderManager().initLoader(FRAGRANCE_LOADER, null, this);

        new FetchCountTask().execute();
        BottomNavigationView buttonNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        buttonNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_medicine:
                        break;
                    case R.id.action_map:
                        Intent map = new Intent(Medicine_Cough.this, MapsActivity.class);
                        startActivity(map);
                        break;
                    case R.id.action_contact:
                        Intent contact = new Intent(Medicine_Cough.this, Feedback.class);
                        startActivity(contact);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, mNotificationsCount);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_notifications: {
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                MedicineContract.MedicineEntry._ID,
                MedicineContract.MedicineEntry.COLUMN_NAME,
                MedicineContract.MedicineEntry.COLUMN_DESCRIPTION,
                MedicineContract.MedicineEntry.COLUMN_IMAGE,
                MedicineContract.MedicineEntry.COLUMN_PRICE,
                MedicineContract.MedicineEntry.COLUMN_USERRATING
        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                MedicineContract.MedicineEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        fragranceAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        fragranceAdapter.swapCursor(null);

    }

    /*
  AsyncTask to fetch the notifications count
  */
    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            String countQuery = "SELECT  * FROM " + CART_TABLE;
            Cursor cursor = mDb.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
            return count;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }
}
