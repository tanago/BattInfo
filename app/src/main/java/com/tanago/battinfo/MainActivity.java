package com.tanago.battinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView field;
    private final Handler handler = new Handler();
    private final Battery battery= new Battery();
    private final BatteryFiles batteryFiles = new BatteryFiles();
    private String statusFile,currentFile,voltageFile,chargeFile,tempFile;

    void getFiles() {
        statusFile=batteryFiles.getStatusFile();
        currentFile=batteryFiles.getCurrentFile();
        voltageFile=batteryFiles.getVoltageFile();
        chargeFile=batteryFiles.getChargeFile();
        tempFile=batteryFiles.getTempFile();
    }


    private final Runnable updater = new Runnable(){
        public void run(){
            fillFields();
            handler.postDelayed(this, UpdateInterval.VALUE);
        }
    };

    private void fillFields(){
        //TODO dont update unsupported (onCreate?)
        printData(battery.getStatus(statusFile), R.id.fieldStatus);
        printData(battery.getCurrent(currentFile), R.id.fieldCurrent);
        printData(battery.getVolt(voltageFile), R.id.fieldVoltage);
        printData(battery.getCharge(chargeFile), R.id.fieldCharge);
        printData(battery.getTemp(tempFile), R.id.fieldTemp);
        System.err.println("updated");
    }

    protected void printData(String s, int i) {
        field = (TextView) findViewById(i);
        if (field != null) field.setText(s);
    }


     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFiles();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        printData(battery.getWear(), R.id.fieldWear);
        if(UpdateInterval.VALUE>0) handler.post(updater);

    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(updater);
        super.onPause();
    }

    public void openSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }
}
