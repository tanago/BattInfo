package com.tanago.battinfo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements Runnable{

    private Battery battery = new Battery();
    private TextView field;
    private int UPDATE_INTERVAL=2000;
    private final Handler handler = new Handler();

    private void debugBatteryFiles(File dir) throws IOException {
        BufferedReader bfr;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) continue;

            bfr = new BufferedReader(new FileReader(file));
            System.err.println(file.getName() + "\t" + bfr.readLine());

        }
    }

    public void run(){
        fillField(R.id.fieldStatus, battery.getStatus());
        fillField(R.id.fieldCurrent, battery.getCurrent());
        fillField(R.id.fieldPercent, battery.getPercentage());
        fillField(R.id.fieldTemp, battery.getTemp());
        fillField(R.id.fieldVoltage, battery.getVolt());
        handler.postDelayed(this, UPDATE_INTERVAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillField(R.id.fieldWear, battery.getWear());
    }

    @Override
    protected void onStart(){
        super.onStart();
        try {
            debugBatteryFiles(new File("/sys/class/power_supply/battery/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        run();
    }

    @Override
    protected void onPause(){
        handler.removeCallbacks(this);
        super.onPause();
    }


    protected void fillField(int i, String s){
        field = (TextView) findViewById(i);
        if (field!=null) field.setText(s);
    }
}
