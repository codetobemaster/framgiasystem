package com.framgia.attendance.logic.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InfoEmailSingleton extends ConfigMail {
    
    private static final long serialVersionUID = 1L;
    private static InfoEmailSingleton instance = null;

    public static InfoEmailSingleton getInstance() throws FileNotFoundException {
        if (instance == null) {
            instance = new InfoEmailSingleton();
            Properties pro = new Properties();
            InputStream input = null;
            ClassLoader classloader =
                    Thread.currentThread().getContextClassLoader();
            input = classloader.getResourceAsStream(MAIL_CONFIG_FILE);
            try {
                pro.load(input);
                instance.HOST_NAME = pro.getProperty("hostName");
                instance.MAIL_FROM = pro.getProperty("mailFrom");
                instance.PORT = pro.getProperty("port");
                instance.USER_NAME = pro.getProperty("userName");
                instance.PASSWORD = pro.getProperty("password");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
