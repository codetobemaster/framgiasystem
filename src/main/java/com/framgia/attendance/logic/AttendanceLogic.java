package com.framgia.attendance.logic;

import java.util.Date;
import java.util.List;

import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dao.TimeInTimeOutDao;
import com.framgia.attendance.dto.TimeInTimeOutDto;

public class AttendanceLogic {
    @Binding
    TimeInTimeOutDao timeIntimOut;

    public List<TimeInTimeOutDto> getListTimeInTimeOut(Date from, Date to) {

        return timeIntimOut.selectAllTimeSheet();
    }

}
