package com.tanago.battinfo;

import java.io.File;

/**
 * Class containing the
 * Created by tanago on 20.8.2016 г..
 */
public class BatteryFiles {

    //TODO check ?exist in new Class (+)only 1 check/(-)more classes
    //public static File batteryStatusFilepath, batteryCurrentFilepath, batteryVoltageFilepath, batteryChargeFilepath, batteryTempFilepath;


    String getStatusFile() {
        if (new File("/sys/class/power_supply/battery/status").exists())
            return "status";
        return "missing";
    }

    String getCurrentFile() {
        if(new File("/sys/class/power_supply/battery/current_now").exists())
            return "current_now";
        else if (new File("/sys/class/power_supply/battery/BatteryAverageCurrent").exists())
            return "BatteryAverageCurrent";
        return "missing";
    }

    String getVoltageFile() {
        if(new File("/sys/class/power_supply/battery/voltage_now").exists())
            return "voltage_now";
        else if (new File("/sys/class/power_supply/battery/batt_vol").exists())
            return "batt_vol";
        return "missing";
    }

    String getChargeFile() {
        if (new File("/sys/class/power_supply/battery/capacity").exists())
            return "capacity";
        return "missing";
    }

    String getTempFile() {
        if(new File("/sys/class/power_supply/battery/temp").exists())
            return "temp";
        else if (new File("/sys/class/power_supply/battery/batt_temp").exists())
            return "batt_temp";
        return "missing";

    }

}
