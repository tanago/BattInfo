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

    //TODO Not static
    private static int temp;

    private static String getInfo(File filePath) {
        try {
            BufferedReader buffRdr = new BufferedReader(new FileReader(filePath));
            return buffRdr.readLine();
        } catch (IOException e) {
            System.err.println("Error while reading " + filePath);
        }
        return "";
    }

    protected static String getStatus() {
        if (BatteryFiles.batteryStatusFilepath.getName().equals("status"))
            return getInfo(BatteryFiles.batteryStatusFilepath);
        else return "Unsupported";
    }

    protected static String getCurrent() {

        if (BatteryFiles.batteryCurrentFilepath.getName().equals("current_now")) {
            temp = Integer.parseInt(getInfo(BatteryFiles.batteryCurrentFilepath));
            System.err.println(temp);

            //TODO check for 5 seconds if its 3 digits format or 6

            if (Math.abs(temp) < 1000) return temp + " mA/h";
            else
                return temp / 1000 + " mA/h";

        } else if (BatteryFiles.batteryCurrentFilepath.getName().equals("BatteryAverageCurrent")) {
            return getInfo(BatteryFiles.batteryCurrentFilepath) + " mA/h";
        } else return "Unsupported";
    }

    protected static String getVolt() {
        if (!BatteryFiles.batteryVoltageFilepath.getName().equals("missing")) {
            temp = Integer.parseInt(getInfo(BatteryFiles.batteryVoltageFilepath)) / 1000;
            if (BatteryFiles.batteryVoltageFilepath.getName().equals("voltage_now"))
                return (double) temp / 1000 + " V";
            else
                // if (BatteryFiles.batteryVoltageFilepath.getName().equals("batt_vol")
                return temp + " V";

        } else return "Unsupported";
    }

    protected static String getWear() {
        File maxCap = new File("/sys/class/power_supply/battery/charge_full");
        File maxCapDesign = new File("/sys/class/power_supply/battery/charge_full_design");


        if (!(maxCap.exists() && maxCapDesign.exists())) {
            System.err.println("Missing Battery Wear file(s)");
            return "Xperia Only!";
        }

        int maxCapInt = Integer.parseInt(getInfo(maxCap));
        int maxCapDesignInt = Integer.parseInt(getInfo(maxCapDesign));
        System.err.println("wear: " + maxCapInt + " / " + maxCapDesignInt);

        int wearlvl = 100 - (maxCapInt / maxCapDesignInt) * 100;
        if (wearlvl < 0) wearlvl = 0;

        return String.valueOf(wearlvl) + "%";
    }

    protected static String getCharge() {
        if (BatteryFiles.batteryChargeFilepath.getName().equals("capacity"))
            return getInfo(BatteryFiles.batteryChargeFilepath) + "%";
        else return "Unsupported";
    }

    protected static String getTemp() {
        if (!BatteryFiles.batteryTempFilepath.getName().equals("missing")) {
            return Double.parseDouble(getInfo(BatteryFiles.batteryTempFilepath)) / 10 + "°";
        } else return "Unsupported";
    }

}
