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
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView field;
    private final Handler handler = new Handler();
    protected static File fileStatus,fileCurrent,fileVoltage,fileCharge,fileTemp;

    private void getFiles(){
        //TODO print unsupported
        //Status
        fileStatus = new File("/sys/class/power_supply/battery/status");

        //Charge %
        fileCharge =  new File("/sys/class/power_supply/battery/capacity");

        //Current
        if(new File("/sys/class/power_supply/battery/current_now").exists())
            fileCurrent=new File("/sys/class/power_supply/battery/current_now");
        else if (new File("/sys/class/power_supply/battery/BatteryAverageCurrent").exists())
            fileCurrent = new File("/sys/class/power_supply/battery/BatteryAverageCurrent");

        //Voltage
        if(new File("/sys/class/power_supply/battery/voltage_now").exists())
            fileVoltage=new File("/sys/class/power_supply/battery/voltage_now");
        else if (new File("/sys/class/power_supply/battery/batt_vol").exists())
            fileVoltage = new File("/sys/class/power_supply/battery/batt_vol");

        //Temperature
        if(new File("/sys/class/power_supply/battery/temp").exists())
            fileTemp=new File("/sys/class/power_supply/battery/temp");
        else if (new File("/sys/class/power_supply/battery/batt_temp").exists())
            fileTemp = new File("/sys/class/power_supply/battery/batt_temp");
    }

    private final Runnable updater = new Runnable(){
        public void run(){
            fillFields();
            handler.postDelayed(this, UpdateInterval.VALUE);
        }
    };

    private void getBatteryStatus(){
        Battery.getStatus();
    }
    private void getBatteryCurrent(){
        Battery.getCurrent();
    }
    private void getBatteryVoltage(){
        Battery.getVolt();
    }
    private void getBatteryWear(){
        Battery.getWear();
    }
    private void getBatteryCharge(){
        Battery.getCharge();
    }
    private void getBatteryTemp(){
        Battery.getTemp();
    }

    private void batteryGetters(){
        getBatteryStatus();
        getBatteryCurrent();
        getBatteryVoltage();
        getBatteryCharge();
        getBatteryTemp();
    }
    private void fillFields(){
        batteryGetters();
        printData(Battery.batteryData[0].toString(), R.id.fieldStatus);
        printData(Battery.batteryData[1].toString(), R.id.fieldCurrent);
        printData(Battery.batteryData[2].toString(), R.id.fieldVoltage);
        printData(Battery.batteryData[3].toString(), R.id.fieldCharge);
        printData(Battery.batteryData[4].toString(), R.id.fieldTemp);
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

        getBatteryWear();
        printData(Battery.getWear(), R.id.fieldWear);

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
