package com.tanago.battinfo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private static SeekBar updateIntervalSeekBar;
    private static TextView interval_value;
    String currentFile, currentData;
    private final Handler handler = new Handler();
    private final BatteryFiles batteryFiles = new BatteryFiles();
    private final Battery battery = new Battery();
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyMgr;
    private final int mNotificationId = 001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        seekBar_Delay();
    }

    private void seekBar_Delay() {
        updateIntervalSeekBar = (SeekBar) findViewById(R.id.seekBar_Delay);
        updateIntervalSeekBar.setProgress(UpdateInterval.VALUE / 1000);

        interval_value = (TextView) findViewById(R.id.interval_value);
        interval_value.setText(String.valueOf(UpdateInterval.VALUE / 1000));

        updateIntervalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                UpdateInterval.VALUE = i * 1000;
                interval_value.setText(String.valueOf(UpdateInterval.VALUE / 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                interval_value.setText(String.valueOf(UpdateInterval.VALUE / 1000));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private final Runnable currentRefresher = new Runnable() {
        public void run() {

            mBuilder.setContentText(battery.getCurrent(currentFile));
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
            handler.postDelayed(this, 5000);
        }
    };

    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void onTrackButtonPress(View v) {
        mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.statusbaricon)
                        .setContentTitle("BattInfo")
                        .setContentText(currentData)
                        .setOngoing(true);
        Intent resultIntent = new Intent(this, SettingsActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        currentFile = batteryFiles.getCurrentFile();
        handler.post(currentRefresher);
        minimizeApp();
    }
}
