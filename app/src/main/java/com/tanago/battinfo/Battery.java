package com.tanago.battinfo;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class with the functions that get information from the Android FS
 * Created by tanago on 1.8.2016 г..
 */
class Battery {

    private String reformat(String prefix, String suffix){
        return prefix.substring(0, prefix.length()-3) + " "+ suffix;
    }

    protected String getStatus(){
       String data="missing";
        try{
            FileReader file = new FileReader("/sys/class/power_supply/battery/status");
            BufferedReader bfrd = new BufferedReader(file);
            data=bfrd.readLine();
        }
        catch(IOException e){
            System.out.println("Error while reading \"status\" file.");
        }
        return data;
    }
    protected String getCurrent(){
        String data="missing";
        try{
            FileReader file = new FileReader("/sys/class/power_supply/battery/current_now");
            BufferedReader bfrd = new BufferedReader(file);
            data=bfrd.readLine();
            data=reformat(data, "mAh");
        }
        catch(IOException e){
            System.out.println("Error while reading \"current\" file.");
        }
        return data;
    }

}
