package com.tanago.battinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static String reformat(String prefix, String suffix){
        return prefix.substring(0, prefix.length()-3) + " "+ suffix;
    }

    private void getStatus(){
        TextView tvS = (TextView) findViewById(R.id.fieldStatus);
        String info="";
        try{
            FileReader file = new FileReader("/sys/class/power_supply/battery/status");
            BufferedReader bfrd = new BufferedReader(file);
            info=bfrd.readLine();
        }
        catch(IOException e){
            System.out.println("Error while reading a file.");
        }
        finally {
            tvS.setText(info);
        }
    }
    private void getCurrent(){
        TextView tvC= (TextView) findViewById(R.id.fieldCurrent);
        String info="";
        try{
            FileReader file = new FileReader("/sys/class/power_supply/battery/current_now");
            BufferedReader bfrd = new BufferedReader(file);
            info=bfrd.readLine();
            info=reformat(info, "mAh");
        }
        catch(IOException e){
            System.out.println("Error while reading a file.");
        }
        finally {
            tvC.setText(info);
        }
    }

    public void buttonOnClick(View v){
        File file = new File("/sys/class/power_supply/battery/");
        Button button = (Button) v;
        if(!file.exists()) {
            button.setText("Device unsupported :(");
            return;
        }
        getStatus();
        getCurrent();


    }

}
