package com.tanago.battinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Battery battery = new Battery();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOnClick(View v) {
        TextView fieldStatus = (TextView) findViewById(R.id.fieldStatus);
        fieldStatus.setText(battery.getStatus());
        TextView fieldCurrent = (TextView) findViewById(R.id.fieldCurrent);
        fieldCurrent.setText(battery.getCurrent());
        TextView fieldPercentage = (TextView) findViewById(R.id.fieldPercent);
        fieldPercentage.setText(battery.getPercentage());
        TextView fieldTemp = (TextView) findViewById(R.id.fieldTemp);
        fieldTemp.setText(battery.getTemp());
    }
}
