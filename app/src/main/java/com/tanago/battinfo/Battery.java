package com.tanago.battinfo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class with the functions that get information from the Android FS
 * Created by tanago on 1.8.2016 г..
 */
class Battery {

    StringBuilder data = new StringBuilder();
    BufferedReader buffRdr;
    File file;

    private void rewriteSB(String info) {
        data = new StringBuilder();
        data.append(info);
    }

    private void getInfo(String filePath) {
        try {
            buffRdr = new BufferedReader(new FileReader(filePath));
            rewriteSB(buffRdr.readLine());
        } catch (IOException e) {
            System.err.println("Error while reading " + filePath);
        }

    }

    protected String getStatus() {
        rewriteSB("missing");
        file = new File("/sys/class/power_supply/battery/status");
        getInfo(file.getAbsolutePath());
        return data.toString();
    }

    protected String getCurrent() {
        rewriteSB("missing");
        file = new File("/sys/class/power_supply/battery/current_now");
        if (! file.exists()) return "missing";

        getInfo(file.getAbsolutePath());
        return data.substring(0, data.length() - 3) + " mA/h";
    }

    protected String getPercentage() {
        rewriteSB("missing");
        file = new File("/sys/class/power_supply/battery/capacity");
        if (! file.exists()) return data.toString();

        getInfo(file.getAbsolutePath());
        data.append("%");
        return data.toString();
    }

    protected String getWear() {
        rewriteSB("missing");
        File maxCap = new File("/sys/class/power_supply/battery/charge_full");
        File maxCapDesign = new File("/sys/class/power_supply/battery/charge_full_design");

        if (!(maxCap.exists() && maxCapDesign.exists())) {
            System.err.print("Missing Battery Wear file(s)");
            return data.toString();
        }
        getInfo(maxCap.getAbsolutePath());
        int maxCapInt = Integer.parseInt(data.toString());
        getInfo(maxCapDesign.getAbsolutePath());
        int maxCapDesignInt = Integer.parseInt(data.toString());

        int wearlvl = 100 - (maxCapInt/maxCapDesignInt) * 100;
        if (wearlvl <0)  wearlvl =0;
        return String.valueOf(wearlvl) + "%";
    }


    protected String getTemp() {
        rewriteSB("missing");
        file = new File("/sys/class/power_supply/battery/temp");
        if (! file.exists()) return data.toString();

        getInfo(file.getAbsolutePath());
        return data.insert(data.length() - 1, ',').append('°').toString();
    }

//    protected String getTempScale(){
//
//        //TODO ( temp
//        rewriteSB("missing");
//        try{
//            FileReader buffRdr = new FileReader("/sys/class/power_supply/battery/capacity");
//            BufferedReader bfrd = new BufferedReader(buffRdr);
//            rewriteSB(bfrd.readLine());
//            data=reformat(data, "mAh");
//        }
//        catch(IOException e){
//            System.out.println("Error while reading \"current\" buffRdr.");
//        }
//        return data.toString();
//    }
}
