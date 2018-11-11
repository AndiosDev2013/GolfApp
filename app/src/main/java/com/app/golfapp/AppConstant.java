package com.app.golfapp;


public class AppConstant {
    // delays
    public static final int DELAY_SPLASH = 2000;
    public static final int DELAY_EXIT = 2000;

    // location
    public static final String TIME_INTERVAL_TITLES[] = {
            "5 seconds",
            "10 seconds",
            "20 seconds",
            "30 seconds",
            "1 minutes",
            "2 minutes",
            "3 minutes",
            "5 minutes",
            "10 minutes",
            "15 minutes",
            "20 minutes",
            "30 minutes"
    };
    public static final int TIME_INTERVAL_VALUES[] = {
            5,
            10,
            20,
            30,
            1 * 60,
            2 * 60,
            3 * 60,
            5 * 60,
            10 * 60,
            15 * 60,
            20 * 60,
            30 * 60,
    };
    public static final int DEFAULT_TIME_INTERVAL_INDEX = 2; // 20 seconds
}
