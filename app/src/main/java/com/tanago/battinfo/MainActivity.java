package com.tanago.battinfo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements Runnable{

    private Battery battery = new Battery();
    private TextView field;
    private int UPDATE_INTERVAL=1000;
    private final Handler handler = new Handler();

    public void run(){
        fillField(R.id.fieldStatus, battery.getStatus());
        fillField(R.id.fieldCurrent, battery.getCurrent());
        fillField(R.id.fieldPercent, battery.getPercentage());
        fillField(R.id.fieldTemp, battery.getTemp());
       fillField(R.id.fieldVoltage, battery.getVolt());
    }

    private void refresh(){
        while(true)
            run();
   }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillField(R.id.fieldWear, battery.getWear());
    }

    @Override
    protected void onResume(){
        super.onResume();
        run();
        handler.postDelayed(this, 1000);
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
