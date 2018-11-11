package com.app.golfapp.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtil {

    public static final boolean WRITE_ENABLE = true;

    public static void writeDebugLog(String tag, String msg) {
        Log.e(tag, msg);

        if (WRITE_ENABLE) {
            String logFilePath = ResourceUtil.getLogFile();
            File file = new File(logFilePath);
            try {
                // BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                String strAddr = tag + "\n";
                buf.append(strAddr);
                String strData = msg + "\n";
                buf.append(strData);
                buf.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}