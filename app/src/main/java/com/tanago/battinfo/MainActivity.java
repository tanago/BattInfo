package com.tanago.battinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements Runnable{

    Battery battery = new Battery();
    TextView field;

    public void run(){
        fillField(R.id.fieldStatus, battery.getStatus());
        fillField(R.id.fieldCurrent, battery.getCurrent());
        fillField(R.id.fieldPercent, battery.getPercentage());
        fillField(R.id.fieldTemp, battery.getTemp());
        fillField(R.id.fieldWear, battery.getWear());
        fillField(R.id.fieldVoltage, battery.getVolt());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }

    protected void fillField(int i, String s){
        field = (TextView) findViewById(i);
        if (field!=null) field.setText(s);

    }

    public void buttonOnClick(View v) throws IOException{


    }
}
