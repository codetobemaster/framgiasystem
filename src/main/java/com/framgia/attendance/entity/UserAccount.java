package com.framgia.attendance.entity;

import java.io.Serializable;
import java.util.Date;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "user_account")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String NO_PERSISTENT_PROPS =
            "password, confirmPassword";

    private Integer userId;
    private String username;
    private Integer employeeId;
    private Employee employee;
    private int userType = 0;
    private String hashedPassword;
    private int defaultLanguage = 0;
    private int deleteFlag;
    private String password = "";
    private String confirmPassword = "";
    private boolean checked = false;
    private Date updatePasswordDate = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        if (employee != null) {
            this.employeeId = employee.getEmployeeId();
        }
    }

    public int getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(int defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Date getUpdatePasswordDate() {
        return updatePasswordDate;
    }

    public void setUpdatePasswordDate(Date updatePasswordDate) {
        this.updatePasswordDate = updatePasswordDate;
    }

}
