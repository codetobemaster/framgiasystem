package com.framgia.attendance.dao;

import java.util.Date;
import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;
import org.seasar.dao.annotation.tiger.SqlFile;

import com.framgia.attendance.entity.Employee;

@S2Dao(bean = Employee.class)
public interface EmployeeDao {

    @SqlFile
    @Arguments({ "key" })
    public List<Employee> listActiveEmployees(String key);
    
    @SqlFile
    @Arguments({ "key" })
    public List<Employee> listActiveAndNotResignedEmployees(String key);

    @SqlFile
    @Arguments({ "segType", "key" })
    public List<Employee> listActiveEmployeesBySegmentType(int segType,
            String key);

    @SqlFile
    @Arguments({ "key" })
    public List<Employee> getEmployeesWithoutUserAccount(String key);

    public static final String selectEmployeesByGroupID_SQL =
            "select em.employee_id from employee em where em.group_id IN /*groups*/(1,2)";

    @Arguments("groups")
    public List<Integer> selectEmployeesByGroupID(List<Integer> groups);

    @Sql("update employee set segment_Type = 5, delete_date=/*deleteDate*/'2014-07-28'  where employee_id in /*employeeIds*/(1,2,3)")
    @Arguments({ "employeeIds", "deleteDate" })
    public int updateResignByIds(List<Integer> employeeIds,
            Date deleteDate);
    
    @Sql("select * from employee e where e.employee_id = /*employeeId*/ and (e.delete_date is null" +
            " or DATE_FORMAT(/*dateNow*/, '%Y%m') <=" +
            " DATE_FORMAT(DATE_ADD(e.delete_date, INTERVAL 1 MONTH), '%Y%m'))")
    @Arguments({ "employeeId", "dateNow" })
    public Employee getActiveEmployee(Integer employeeId, Date dateNow);

    @Sql("update employee set group_id = null where group_id in /*groupIds*/(1,2,3)")
    @Arguments({ "groupIds" })
    public int updateGroupId(List<Integer> groupIds);

    @Sql("SELECT LAST_INSERT_ID()")
    public int getLastId();

    @SqlFile
    @Arguments({ "employeeNumber", "key" })
    public Employee selectEmployeeByEmployeeNumber(String employeeNumber,
            String key);

    @SqlFile
    @Arguments({ "emailAddress", "key" })
    public Employee selectEmployeeByEmailAddress(String emailAddress);

    @SqlFile
    @Arguments({ "employeeId", "key" })
    public Employee getEmployeeById(Integer employeeId, String key);

    @SqlFile
    @Arguments({ "employee", "key" })
    public void insertWithEncodeRank(Employee employee, String key);

    @SqlFile
    @Arguments({ "employee", "key" })
    public void updateWithEncodeRank(Employee employee, String key);
    
    @SqlFile
    @Arguments({"employee"})
    public void updatePayVacationLeft(Employee employee);
    
    @SqlFile
    @Arguments({"rankCd", "key"})
    public List<Employee> getEmployeeByRankCd(String rankCd, String key);

}
