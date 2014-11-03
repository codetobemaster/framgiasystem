package com.framgia.attendance.dao;

import java.util.List;

import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;

import com.framgia.attendance.dto.TimeInTimeOutDto;
import com.framgia.attendance.entity.TimeInTimeOut;


@S2Dao(bean = TimeInTimeOut.class)
public interface TimeInTimeOutDao {

	public List<TimeInTimeOut> findAll();
	@Sql("Select t.*, e.EmployeeName  from tblTimeInTimeOut t join tblEmployee e On e.EmployeeID=t.EmployeeID where t.WorkingDay>='2014-01-01' order by t.WorkingDay, t.EmployeeId  DESC")
	public List<TimeInTimeOutDto> selectAllTimeSheet();
	
	

}
