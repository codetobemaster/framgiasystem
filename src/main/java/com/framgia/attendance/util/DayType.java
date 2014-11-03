package com.framgia.attendance.util;

public enum DayType {

    WEEKDAY(0,"app.dayType.weekday"), 
    REGULAR_HOLIDAY(1, "app.dayType.regularHoliday"), 
    LEGAL_HOLIDAY(2, "app.dayType.legalHoliday");
   
    private int value;
    private String messageKey;
    
    DayType(int value, String messageKey){
        this.value = value;
        this.messageKey = messageKey;
    }
    
    public static String getMessageKey(Integer value) {
        if (null == value) {
            return null;
        }
        String ret = "";
        for (DayType type : DayType.values()) {
            if (type.getValue() == value) {
                return type.getMessageKey();
            }
        }
        return ret;
    }
    
    
    public int getValue(){
        return value;
    }
    
    public int value(){
        return value;
    }
    
    public String getMessageKey() {
        return messageKey;
    }
}
