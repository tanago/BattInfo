package com.tanago.battinfo;

import android.Manifest;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements Runnable {

    private Battery battery = new Battery();
    private TextView field;
    private int UPDATE_INTERVAL = 2000;
    private final Handler handler = new Handler();

    private void debugBatteryFiles(File dir) {
        BufferedReader bfr;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getPath() + "/battinfodebug.txt"));
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) continue;

                bfr = new BufferedReader(new FileReader(file));
                writer.write(file.getName() + "\t" + bfr.readLine());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        handler.postDelayed(this, UPDATE_INTERVAL);
        fillField(R.id.fieldStatus, battery.getStatus());
        fillField(R.id.fieldCurrent, battery.getCurrent());
        fillField(R.id.fieldPercent, battery.getPercentage());
        fillField(R.id.fieldTemp, battery.getTemp());
        fillField(R.id.fieldVoltage, battery.getVolt());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillField(R.id.fieldWear, battery.getWear());
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        debugBatteryFiles(new File("/sys/class/power_supply/battery/"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        run();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(this);
        super.onPause();
    }


    protected void fillField(int i, String s) {
        field = (TextView) findViewById(i);
        if (field != null) field.setText(s);
    }
}
