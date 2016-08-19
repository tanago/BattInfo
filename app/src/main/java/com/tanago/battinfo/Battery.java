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

    //protected static StringBuilder dataStatus, dataCurrent, dataVoltage, dataCharge, dataTemp;
    protected static StringBuilder[] batteryData = new StringBuilder[5];

    private static String getInfo(String filePath) {
        String temp;
        try {
            BufferedReader buffRdr = new BufferedReader(new FileReader(filePath));
            temp = buffRdr.readLine();
            System.err.println("GetInfo() of " + filePath + " returned: " + temp);
            return temp;
        } catch (IOException e) {
            System.err.println("Error while reading " + filePath);
        }
        System.err.println("BUllSHIET");
        return "";
    }

    protected static String getStatus() {
        batteryData[0] = new StringBuilder();
        batteryData[0].append(getInfo(MainActivity.fileStatus.getAbsolutePath()));
        return batteryData[0].toString();
    }

    protected static String getCurrent() {
        batteryData[1] = new StringBuilder();

        if (MainActivity.fileCurrent.getName().equals("current_now")) {
            batteryData[1].append(getInfo(MainActivity.fileCurrent.getAbsolutePath()));

            if (batteryData[1].length() < 5)
                return batteryData[1].toString() + " mA/h";

            else return batteryData[1].substring(0, batteryData[1].length() - 3) + " mA/h";

        } else {

            System.err.println("entered else");
            //if (MainActivity.fileCurrent.getName() == "BatteryAverageCurrent")
            batteryData[1].append(getInfo(MainActivity.fileCurrent.getAbsolutePath()));
            return batteryData[1].toString() + " mA/h";
        }
    }

    protected static String getVolt() {
        batteryData[2] = new StringBuilder();

        if (MainActivity.fileVoltage.getName().equals("voltage_now")) {
            batteryData[2].append(getInfo(MainActivity.fileVoltage.getAbsolutePath()));
            return batteryData[2].delete(batteryData[2].length() - 4, batteryData[2].length() - 1).insert(1, ',').append(" V").toString();
        } else {
            //if (MainActivity.fileVoltage.getName() == "batt_vol") {

            batteryData[2].append(getInfo(MainActivity.fileVoltage.getAbsolutePath()));
            return batteryData[2].insert(1, ',').append(" V").toString();
        }
    }

    protected static String getWear() {
        File maxCap = new File("/sys/class/power_supply/battery/charge_full");
        File maxCapDesign = new File("/sys/class/power_supply/battery/charge_full_design");


        if (!(maxCap.exists() && maxCapDesign.exists())) {
            System.err.println("Missing Battery Wear file(s)");
            return "Xperia Only!";
        }

        int maxCapInt = Integer.parseInt(getInfo(maxCap.getAbsolutePath()));
        int maxCapDesignInt = Integer.parseInt(getInfo(maxCapDesign.getAbsolutePath()));
        System.err.println("wear: " + maxCapInt + " / " + maxCapDesignInt);

        int wearlvl = 100 - (maxCapInt / maxCapDesignInt) * 100;
        if (wearlvl < 0) wearlvl = 0;

        return String.valueOf(wearlvl) + "%";
    }

    protected static String getCharge() {
        batteryData[3] = new StringBuilder();
        batteryData[3].append(getInfo(MainActivity.fileCharge.getAbsolutePath()));

        return batteryData[3].append("%").toString();
    }

    protected static String getTemp() {
        batteryData[4] = new StringBuilder();

        batteryData[4].append(getInfo(MainActivity.fileTemp.getAbsolutePath()));

        return batteryData[4].insert(batteryData[4].length() - 1, ',').append('°').toString();
    }

}
