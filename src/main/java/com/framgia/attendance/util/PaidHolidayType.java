package com.framgia.attendance.util;

public enum PaidHolidayType {

    NONE(0, "app.paidHolidayType.none", 0.0),
    FULL_DAY(3, "app.paidHoliday.fullDay", 1.0),
    HALF(4, "app.paidHoliday.half", 0.5),
    VACATION(5, "app.paidHoliday.vacation", 1.0),
    CEREMONIAL(6, "app.paidHoliday.ceremonial", 1.0);

    private int value;
    private String messageKey;
    private Double percent;

    private PaidHolidayType(int value, String messageKey, Double percent) {
        this.value = value;
        this.messageKey = messageKey;
        this.percent = percent;
    }

    public int getValue() {
        return value;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Double getPercentHours() {
        return percent;
    }

    public static String getMessageKey(Integer value) {
        if (null == value) {
            return null;
        }
        String ret = "";
        for (PaidHolidayType type : PaidHolidayType.values()) {
            if (type.getValue() == value) {
                return type.getMessageKey();
            }
        }
        return ret;
    }

    public static PaidHolidayType getByValue(Integer value) {
        if (null == value) {
            return null;
        }
        for (PaidHolidayType type : PaidHolidayType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    public static int getAddMinutesByValue(Integer value, Double workHoursPerDay) {
        if (null == value) {
            return 0;
        }
        double minsPerHour = 60.0;
        for (PaidHolidayType type : PaidHolidayType.values()) {
            if (type.getValue() == value) {
                return (int)(type.getPercentHours() * workHoursPerDay * minsPerHour);
            }
        }
        return 0;
    }

}
