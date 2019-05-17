package com.arunrk.boradcost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NumberReceiver extends BroadcastReceiver {

    private static final String TAG = "MainActivity";

    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            dbHelper.save(database, number);
            Log.d(TAG, "onReceive: " + number);

            Cursor cursor = dbHelper.readNumber(database);
            Log.d(TAG, "onReceive: cursor size " + cursor.getCount());
            dbHelper.close();
        }

        Intent intent1 =  new Intent(DbHelper.UPDATE_UI_FILTER);
        context.sendBroadcast(intent1);

    }

}
