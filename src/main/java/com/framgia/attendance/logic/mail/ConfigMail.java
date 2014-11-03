package com.framgia.attendance.logic.mail;

import java.io.Serializable;

public class ConfigMail implements Serializable {
    public static final String MAIL_CONFIG_FILE = "mail_config.properties";
    public String DEDAULT_MAIL;
    public String HOST_NAME;
    public String PORT;
    public String USER_NAME;
    public String PASSWORD;
    public String MAIL_FROM;

}
