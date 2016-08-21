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

    private final String battDir = "/sys/class/power_supply/battery/";
    private int temporary;

    private String getInfo(String filePath) {
        try {
            BufferedReader buffRdr = new BufferedReader(new FileReader(filePath));
            return buffRdr.readLine();
        } catch (IOException e) {
            System.err.println("Error while reading " + filePath);
        }
        return "";
    }

    protected String getStatus(String filename) {
        if (filename.equals("status"))
            return getInfo(battDir + filename);
        else return "Unsupported";
    }

    protected String getCurrent(String filename) {

        if (filename.equals("current_now")) {
            temporary = Integer.parseInt(getInfo(battDir + filename));
            System.err.println(temporary);

            //TODO check for 5 seconds if its 3 digits format or 6

            if (Math.abs(temporary) < 2000) return temporary + " mA/h";
            else
                return temporary / 1000 + " mA/h";

        } else if (filename.equals("BatteryAverageCurrent") || filename.equals("batt_current")) {
            return getInfo(battDir + filename) + " mA/h";

        }
        else return "Unsupported";
    }

    protected String getVolt(String filename) {
        if (!filename.equals("missing")) {
            temporary = Integer.parseInt(getInfo(battDir + filename)) / 1000;
            if (filename.equals("voltage_now"))
                return (double) temporary / 1000 + " V";
            else
                // if (filename.equals("batt_vol")
                return temporary + " V";

        } else return "Unsupported";
    }

    protected String getWear() {
        File maxCap = new File("/sys/class/power_supply/battery/charge_full");
        File maxCapDesign = new File("/sys/class/power_supply/battery/charge_full_design");


        if (!(maxCap.exists() && maxCapDesign.exists())) {
            System.err.println("Missing Battery Wear file(s)");
            return "Unsupported";
        }

        int maxCapInt = Integer.parseInt(getInfo(maxCap.getAbsolutePath()));
        int maxCapDesignInt = Integer.parseInt(getInfo(maxCapDesign.getAbsolutePath()));
        System.err.println("wear: " + maxCapInt + " / " + maxCapDesignInt);

        int wearlvl = 100 - (maxCapInt / maxCapDesignInt) * 100;
        if (wearlvl < 0) wearlvl = 0;

        return String.valueOf(wearlvl) + "%";
    }

    protected String getCharge(String filename) {
        if (filename.equals("capacity"))
            return getInfo(battDir + filename) + "%";
        else return "Unsupported";
    }

    protected String getTemp(String filename) {
        if (filename.equals("temp") || filename.equals("batt_temp")) {
            return Double.parseDouble(getInfo(battDir + filename)) / 10 + "°";
        } else return "Unsupported";
    }

}
