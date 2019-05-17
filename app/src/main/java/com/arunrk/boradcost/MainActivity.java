package com.arunrk.boradcost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private TextView txtErr;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<MobileNumber> mList;
    private NumberAdapter mAdapter;

    private DbHelper mDbHelper;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtErr = findViewById(R.id.txtErr);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mDbHelper = new DbHelper(this);
        mList = new ArrayList<>();
        mAdapter = new NumberAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        readNumber();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                readNumber();
            }
        };
    }

    private void readNumber() {
        mList.clear();
        Log.d(TAG, "readNumber: before " + mList.size());
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        Cursor cursor = mDbHelper.readNumber(database);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String number;
                int id;
                id = cursor.getInt(cursor.getColumnIndex(DbHelper.KEY_ID));
                number = cursor.getString(cursor.getColumnIndex(DbHelper.KEY_MOBILE_NUM));
                mList.add(new MobileNumber(id, number));
            }
        }
        Log.d(TAG, "readNumber: after " + mList.size());
        cursor.close();
        mDbHelper.close();

        mAdapter.notifyDataSetChanged();
        //  mRecyclerView.setVisibility(View.VISIBLE);
        //txtErr.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(DbHelper.UPDATE_UI_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
