package com.tq.alarmmanagerdemo;

import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView text = null;
    private AlarmManager alarmManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        AlarmManager.AlarmClockInfo clockInfo = alarmManager.getNextAlarmClock();
        text.setText(printAlarmClockInfo(clockInfo));

        printProviders(this);
        printAlarms(this);
    }

    // 打印全部provider
    public void printProviders(Context context) {
        for (PackageInfo pkg : context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
            ProviderInfo[] providers = pkg.providers;
            if (providers == null) {
                continue;
            }

            for (ProviderInfo provider : providers) {
                Log.d("LOG_TAG", "provider: " + provider.authority);
            }
        }
    }

    public void printAlarms(Context context) {
        final String LOG_TAG = "LOG_TAG";
        Uri uri = Uri.parse("content://com.android.alarmclock/alarm");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor == null) {
            Log.i(LOG_TAG, "no alrams");
            return;
        }

        Log.i(LOG_TAG, "no. of records are " + cursor.getCount());
        Log.i(LOG_TAG, "no. of columns are " + cursor.getColumnCount());

        String columnNames[] = cursor.getColumnNames();
        for (String name : columnNames) {
            Log.i(LOG_TAG, name);
        }

        boolean hasElement;
        for (hasElement = cursor.moveToFirst();
             hasElement;
             hasElement = cursor.moveToNext()) {
            for (int j = 0; j < cursor.getColumnCount(); j++) {
                Log.i(LOG_TAG, cursor.getColumnName(j) + " which has value " + cursor.getString(j));
            }
        }
    }

    private String printAlarmClockInfo(AlarmManager.AlarmClockInfo clockInfo) {
//        String text = "NULL";
//        if (clockInfo != null) {
//            text = clockInfo.toString();
//        }
//
//        StringBuilder builder = new StringBuilder();
//        builder.append(clockInfo.getShowIntent())
//
//        return text;
        return "";
    }
}
