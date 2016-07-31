package com.tanago.battinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void test(){
        int i = 0;
    }

    private void getCurrent(){
        TextView textView = (TextView) findViewById(R.id.fieldCurrent);
        String info="Not Found!";
        try{
            FileReader file = new FileReader("/sys/class/power_supply/battery/current_now");
            BufferedReader bfrd = new BufferedReader(file);
            info=bfrd.readLine();
        }
        catch(IOException e){
            System.out.println("Error while reading a file.");
        }

        textView.setText(info);
    }
}
