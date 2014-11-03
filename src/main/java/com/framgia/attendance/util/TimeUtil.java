package com.framgia.attendance.util;

public class TimeUtil {

    /**
     * Returns string HH:MM for
     * 
     * @param hhmm
     * @return
     */
    public static String toTimeStr(Integer hhmm) {
        if (hhmm != null) {
            int hh = hhmm / 100;
            int mm = hhmm % 100;
            return hh + ":" + String.format("%02d", mm);
        } else {
            return null;
        }
    }

    public static String minToHHMM(Integer mins) {
        if (mins != null) {
            int hh = mins / 60;
            int mm = mins % 60;
            return hh + ":" + String.format("%02d", mm);
        } else {
            return null;
        }

    }
}
