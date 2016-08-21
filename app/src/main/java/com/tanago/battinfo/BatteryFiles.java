package com.tanago.battinfo;

import java.io.File;

/**
 * Class containing the
 * Created by tanago on 20.8.2016 г..
 */
public class BatteryFiles {

    //TODO check ?exist in new Class (+)only 1 check/(-)more classes
    public static File batteryStatusFilepath, batteryCurrentFilepath, batteryVoltageFilepath, batteryChargeFilepath, batteryTempFilepath;

    static void getFiles() {
        getStatusFile();
        getCurrentFile();
        getVoltageFile();
        getChargeFile();
        getTempFile();
    }

    private static void getStatusFile() {
        if (new File("/sys/class/power_supply/battery/status").exists())
            batteryStatusFilepath = new File("/sys/class/power_supply/battery/status");
        else batteryStatusFilepath = new File("missing");
    }

    private static void getCurrentFile() {
        if(new File("/sys/class/power_supply/battery/current_now").exists())
            batteryCurrentFilepath=new File("/sys/class/power_supply/battery/current_now");
        else if (new File("/sys/class/power_supply/battery/BatteryAverageCurrent").exists())
            batteryCurrentFilepath = new File("/sys/class/power_supply/battery/BatteryAverageCurrent");
        else batteryCurrentFilepath = new File("missing");
    }

    private static void getVoltageFile() {
        if(new File("/sys/class/power_supply/battery/voltage_now").exists())
            batteryVoltageFilepath=new File("/sys/class/power_supply/battery/voltage_now");
        else if (new File("/sys/class/power_supply/battery/batt_vol").exists())
            batteryVoltageFilepath = new File("/sys/class/power_supply/battery/batt_vol");
        else batteryVoltageFilepath = new File("missing");
    }

    private static void getChargeFile() {
        if (new File("/sys/class/power_supply/battery/capacity").exists())
            batteryChargeFilepath = new File("/sys/class/power_supply/battery/capacity");
        else batteryChargeFilepath = new File("missing");
    }

    private static void getTempFile() {
        if(new File("/sys/class/power_supply/battery/temp").exists())
            batteryTempFilepath=new File("/sys/class/power_supply/battery/temp");
        else if (new File("/sys/class/power_supply/battery/batt_temp").exists())
            batteryTempFilepath = new File("/sys/class/power_supply/battery/batt_temp");
        else batteryTempFilepath = new File("missing");

    }

}
