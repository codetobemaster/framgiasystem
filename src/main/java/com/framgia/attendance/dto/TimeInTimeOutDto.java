package com.framgia.attendance.dto;

import java.io.Serializable;
import java.util.Date;

public class TimeInTimeOutDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer TimeID;
	private String EmployeeID;
	private Date TimeIn;
	private Date TimeOut;
	private Date WorkingDay;
	private String EmployeeName;

	public Integer getTimeID() {
		return TimeID;
	}

	public void setTimeID(Integer timeID) {
		TimeID = timeID;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}

	public Date getTimeIn() {
		return TimeIn;
	}

	public void setTimeIn(Date timeIn) {
		TimeIn = timeIn;
	}

	public Date getTimeOut() {
		return TimeOut;
	}

	public void setTimeOut(Date timeOut) {
		TimeOut = timeOut;
	}

	public Date getWorkingDay() {
		return WorkingDay;
	}

	public void setWorkingDay(Date workingDay) {
		WorkingDay = workingDay;
	}

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }


}
