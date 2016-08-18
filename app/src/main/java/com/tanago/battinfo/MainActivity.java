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


public class MainActivity extends AppCompatActivity implements Runnable {

    private Battery battery = new Battery();
    private TextView field;
    private final Handler handler = new Handler();

    protected void fillField(int i, String s) {
        field = (TextView) findViewById(i);
        if (field != null) field.setText(s);
    }

    public void run() {
        handler.postDelayed(this, UpdateInterval.VALUE);
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

        fillField(R.id.fieldWear, battery.getWear());

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
        run();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(this);
        super.onPause();
    }

    public void openSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }
}
