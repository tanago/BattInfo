package com.tanago.battinfo;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class with the functions that get information from the Android FS
 * Created by tanago on 1.8.2016 г..
 */
class Battery {

    StringBuilder data = new StringBuilder();
    BufferedReader file;

    private void rewriteSB( String info ){
        data.delete(0, data.length());
        data.append(info);
    }

    private void getInfo(String filePath){
        try{
            file = new BufferedReader(new FileReader(filePath));
            rewriteSB(file.readLine());
        }
        catch(IOException e){
            System.err.println("Error while reading " + filePath);
        }

    }

/* TODO
    private String reformat(String prefix, String suffix){
        return prefix + " "+ suffix;
    }
*/

    protected String getStatus(){
        rewriteSB("missing");
        getInfo("/sys/class/power_supply/battery/status");
        return data.toString();
    }

    protected String getCurrent(){
        rewriteSB("missing");
        getInfo("/sys/class/power_supply/battery/current_now");
        return data.substring(0,data.length()-3).toString()+ " mA/h";
    }

    protected String getPercentage(){
        rewriteSB("missing");
        getInfo("/sys/class/power_supply/battery/capacity");
        data.append("%");
        return data.toString();
    }

//         protected String getWear(){
//
//        //TODO x=100-(charge_full/charge_full_design)*100 (if x<0  x = 0 )
//        rewriteSB("missing");
//        try{
//            FileReader file = new FileReader("/sys/class/power_supply/battery/capacity");
//            BufferedReader bfrd = new BufferedReader(file);
//            rewriteSB(bfrd.readLine());
//            data=reformat(data, "mAh");
//        }
//        catch(IOException e){
//            System.out.println("Error while reading \"current\" file.");
//        }
//        return data.toString();
//    }


    protected String getTemp(){
        rewriteSB("missing");
        getInfo("/sys/class/power_supply/battery/temp");
        return data.insert(data.length()-1, ',').append('°').toString();
    }

//    protected String getTempScale(){
//
//        //TODO ( temp
//        rewriteSB("missing");
//        try{
//            FileReader file = new FileReader("/sys/class/power_supply/battery/capacity");
//            BufferedReader bfrd = new BufferedReader(file);
//            rewriteSB(bfrd.readLine());
//            data=reformat(data, "mAh");
//        }
//        catch(IOException e){
//            System.out.println("Error while reading \"current\" file.");
//        }
//        return data.toString();
//    }
}
