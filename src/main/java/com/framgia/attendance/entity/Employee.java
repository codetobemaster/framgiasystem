package com.framgia.attendance.entity;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer employeeId;
    private String employeeNumber;
    private String lastNameKanji;
    private String firstNameKanji;
    private String lastNameKana;
    private String firstNameKana;
    private String lastNameEng;
    private String firstNameEng;
    private String middleNameEng;
    private String emailAddress;
    private Date employmentDate;
    private Integer groupId;
    private Integer activeFlag = 1;
    private Boolean checked = false;
    private Integer segmentType;
    private Double payVacationLeftJoin;
    private Double payVacationLeftJoinTemp;
    private Double payVacationLeftThisYear;
    private Double payVacationLeftLastYear;
    private Date payVacationLeftJoinTempDate;
    private Date payVacationLeftLastYearDate;
    private Date payVacationLeftthisYearDate;
    private String rankCd;
    private Date deleteDate;
    private Double workHoursPerDay;
    
    public Double getWorkHoursPerDay() {
        return workHoursPerDay;
    }

    public void setWorkHoursPerDay(Double workHourPerDay) {
        this.workHoursPerDay = workHourPerDay;
    }
    
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLastNameKanji() {
        return lastNameKanji;
    }

    public void setLastNameKanji(String lastNameKanji) {
        this.lastNameKanji = lastNameKanji;
    }

    public String getFirstNameKanji() {
        return firstNameKanji;
    }

    public void setFirstNameKanji(String firstNameKanji) {
        this.firstNameKanji = firstNameKanji;
    }

    public String getLastNameKana() {
        return lastNameKana;
    }

    public void setLastNameKana(String lastNameKana) {
        this.lastNameKana = lastNameKana;
    }

    public String getFirstNameKana() {
        return firstNameKana;
    }

    public void setFirstNameKana(String firstNameKana) {
        this.firstNameKana = firstNameKana;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getMiddleNameEng() {
        return middleNameEng;
    }

    public void setMiddleNameEng(String middleNameEng) {
        this.middleNameEng = middleNameEng;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getActiveFlag() {
        return activeFlag;
    }

    public Boolean getChecked() {
		return checked;
	}

	public Integer getSegmentType() {
		return segmentType;
	}

	public void setSegmentType(Integer segmentType) {
		this.segmentType = segmentType;
	}

	public Double getPayVacationLeftJoin() {
		return payVacationLeftJoin;
	}

    public void setPayVacationLeftJoin(Double payVacationLeftJoin) {
        this.payVacationLeftJoin = payVacationLeftJoin;
    }

	public Double getPayVacationLeftJoinTemp() {
        return payVacationLeftJoinTemp;
    }

    public void setPayVacationLeftJoinTemp(Double payVacationLeftJoinTemp) {
        this.payVacationLeftJoinTemp = payVacationLeftJoinTemp;
    }

    public Double getPayVacationLeftThisYear() {
        return payVacationLeftThisYear;
    }

    public void setPayVacationLeftThisYear(Double payVacationLeftThisYear) {
        this.payVacationLeftThisYear = payVacationLeftThisYear;
    }

    public Double getPayVacationLeftLastYear() {
        return payVacationLeftLastYear;
    }

    public void setPayVacationLeftLastYear(Double payVacationLeftLastYear) {
        this.payVacationLeftLastYear = payVacationLeftLastYear;
    }

	public Date getPayVacationLeftJoinTempDate() {
        return payVacationLeftJoinTempDate;
    }

    public void setPayVacationLeftJoinTempDate(Date payVacationLeftJoinTempDate) {
        this.payVacationLeftJoinTempDate = payVacationLeftJoinTempDate;
    }

    public Date getPayVacationLeftLastYearDate() {
        return payVacationLeftLastYearDate;
    }

    public void setPayVacationLeftLastYearDate(Date payVacationLeftLastYearDate) {
        this.payVacationLeftLastYearDate = payVacationLeftLastYearDate;
    }

    public Date getPayVacationLeftThisYearDate() {
        return payVacationLeftthisYearDate;
    }

    public void setPayVacationLeftThisYearDate(Date payVacationLeftthisYearDate) {
        this.payVacationLeftthisYearDate = payVacationLeftthisYearDate;
    }

    public String getRankCd() {
		return rankCd;
	}

	public void setRankCd(String rankCd) {
		this.rankCd = rankCd;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getFullNameKanji() {
        return this.lastNameKanji + " " + firstNameKanji;
    }

    public String getFullNameKanjiWithNumber() {
        return this.lastNameKanji + " " + firstNameKanji + "(" + employeeNumber
                + ")";
    }

    public String getFullNameEng(){
        return this.lastNameEng + " " + firstNameEng;
    }
    
    public String getFirstDotLastName() {
        return StringUtils.lowerCase(firstNameEng) + "." + StringUtils.lowerCase(lastNameEng);
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setdeleteDate(Date lastUpdateDate) {
        this.deleteDate = lastUpdateDate;
    }
    
    public double getPayVacationLeft(){
        return (this.payVacationLeftJoin == null ? 0.0
                : this.payVacationLeftJoin)
                + (this.payVacationLeftJoinTemp == null ? 0.0
                        : this.payVacationLeftJoinTemp)
                + (this.payVacationLeftLastYear == null ? 0.0
                        : this.payVacationLeftLastYear)
                + (this.payVacationLeftThisYear == null ? 0.0
                        : this.payVacationLeftThisYear);
    }
}
