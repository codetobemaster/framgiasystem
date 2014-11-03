package com.framgia.attendance.logic.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AttendanceEmailSingleton extends ConfigMail {

    private static final long serialVersionUID = 1L;
    private static AttendanceEmailSingleton instance = null;

    public static AttendanceEmailSingleton getInstance()
            throws FileNotFoundException {
        if (instance == null) {
            instance = new AttendanceEmailSingleton();
            Properties pro = new Properties();
            InputStream input = null;
            ClassLoader classloader =
                    Thread.currentThread().getContextClassLoader();
            input = classloader.getResourceAsStream(MAIL_CONFIG_FILE);
            try {
                pro.load(input);
                instance.HOST_NAME = pro.getProperty("hostNameAttendance");
                instance.MAIL_FROM = pro.getProperty("mailFromAttendance");
                instance.PORT = pro.getProperty("portAttendance");
                instance.USER_NAME = pro.getProperty("userNameAttendance");
                instance.PASSWORD = pro.getProperty("passwordAttendance");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
