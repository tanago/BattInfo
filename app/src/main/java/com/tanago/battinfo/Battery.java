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
        rewriteSB("Unsupported");
        file = new File("/sys/class/power_supply/battery/status");
        getInfo(file.getAbsolutePath());
        return data.toString();
    }

    protected String getCurrent() {
        rewriteSB("Unsupported");

        file = new File("/sys/class/power_supply/battery/current_now");
        if (file.exists()) {
            getInfo(file.getAbsolutePath());
            if(data.length() < 6) return data + " mA/h";
            else return data.substring(0, data.length() - 3) + " mA/h";
        }

        file = new File("/sys/class/power_supply/battery/BatteryAverageCurrent");
        if (file.exists()) {
            getInfo(file.getAbsolutePath());
            return data.toString() + " mA/h";
        }

        return data.toString();
    }

    protected String getPercentage() {
        rewriteSB("Unsupported");
        file = new File("/sys/class/power_supply/battery/capacity");
        if (!file.exists()) return data.toString();

        getInfo(file.getAbsolutePath());
        data.append("%");
        return data.toString();
    }

    protected String getWear() {
        rewriteSB("Xperia only");
        File maxCap = new File("/sys/class/power_supply/battery/charge_full");
        File maxCapDesign = new File("/sys/class/power_supply/battery/charge_full_design");

        if (!(maxCap.exists() && maxCapDesign.exists())) {
            System.err.println("Missing Battery Wear file(s)");
            return data.toString();
        }
        getInfo(maxCap.getAbsolutePath());
        int maxCapInt = Integer.parseInt(data.toString());
        getInfo(maxCapDesign.getAbsolutePath());
        int maxCapDesignInt = Integer.parseInt(data.toString());

        int wearlvl = 100 - (maxCapInt / maxCapDesignInt) * 100;
        if (wearlvl < 0) wearlvl = 0;
        return String.valueOf(wearlvl) + "%";
    }


    protected String getTemp() {
        rewriteSB("Unsupported");

        file = new File("/sys/class/power_supply/battery/temp");
        if (file.exists()) {
            getInfo(file.getAbsolutePath());
            return data.insert(data.length() - 1, ',').append('°').toString();
        }

        file = new File("/sys/class/power_supply/battery/batt_temp");
        if (file.exists()) {
            getInfo(file.getAbsolutePath());
            return data.insert(data.length() - 1, ',').append('°').toString();
        }

        return data.toString();
    }

    protected String getVolt() {
        rewriteSB("Unsupported");

        file = new File("/sys/class/power_supply/battery/voltage_now");
        if (file.exists()) {
            getInfo(file.getAbsolutePath());
            return data.delete(data.length() - 4, data.length() - 1).insert(1, ',').append(" V").toString();
        }

        file = new File("/sys/class/power_supply/battery/batt_vol");
        if (file.exists()) {
            getInfo(file.getAbsolutePath());
            return data.insert(1, ',').append(" V").toString();
        }

        return data.toString();
    }
}
