package com.tanago.battinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private static SeekBar updateIntervalSeekBar;
    private static TextView interval_value;


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
}
