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
        return prefix + " "+ suffix;
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
            data=reformat(data.substring(0, data.length()-3), "mAh");
        }
        catch(IOException e){
            System.out.println("Error while reading \"current\" file.");
        }
        return data;
    }

    protected String getPercentage(){
        String data="missing";
        try{
            FileReader file = new FileReader("/sys/class/power_supply/battery/capacity");
            BufferedReader bfrd = new BufferedReader(file);
            data=bfrd.readLine();
            data=reformat(data, "%");
        }
        catch(IOException e){
            System.out.println("Error while reading \"capacity\" file.");
        }
        return data;
    }

//         protected String getWear(){
//
//        //TODO x=100-(charge_full/charge_full_design)*100 (if x<0  x = 0 )
//        String data="missing";
//        try{
//            FileReader file = new FileReader("/sys/class/power_supply/battery/capacity");
//            BufferedReader bfrd = new BufferedReader(file);
//            data=bfrd.readLine();
//            data=reformat(data, "mAh");
//        }
//        catch(IOException e){
//            System.out.println("Error while reading \"current\" file.");
//        }
//        return data;
//    }


    protected String getTemp(){
        String data="missing";
        try{
            FileReader file = new FileReader("/sys/class/power_supply/battery/temp");
            BufferedReader bfrd = new BufferedReader(file);
            data=bfrd.readLine();
            data=reformat(data, "°");
        }
        catch(IOException e){
            System.out.println("Error while reading \"temp\" file.");
        }
        return data;
    }

//    protected String getTempScale(){
//
//        //TODO ( temp
//        String data="missing";
//        try{
//            FileReader file = new FileReader("/sys/class/power_supply/battery/capacity");
//            BufferedReader bfrd = new BufferedReader(file);
//            data=bfrd.readLine();
//            data=reformat(data, "mAh");
//        }
//        catch(IOException e){
//            System.out.println("Error while reading \"current\" file.");
//        }
//        return data;
//    }
}
