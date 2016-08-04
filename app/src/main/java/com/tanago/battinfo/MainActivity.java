package com.tanago.battinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Battery battery = new Battery();
    TextView field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void fillField(int i, String s){
        field = (TextView) findViewById(i);
        if (field!=null) field.setText(s);

    }
    public void buttonOnClick(View v) throws IOException{
        fillField(R.id.fieldStatus, battery.getStatus());
        fillField(R.id.fieldCurrent, battery.getCurrent());
        fillField(R.id.fieldPercent, battery.getPercentage());
        fillField(R.id.fieldTemp, battery.getTemp());
        fillField(R.id.fieldWear, battery.getWear());
        fillField(R.id.fieldVoltage, battery.getVolt());

        File files = new File("/sys/class/power_supply/battery/");
        BufferedReader bfr;
        for ( File file : files.listFiles()){
            if(file.isDirectory()) continue;

            bfr = new BufferedReader(new FileReader(file));
            System.err.println(file.getName() + "\t" + bfr.readLine() );
        }
    }
}
