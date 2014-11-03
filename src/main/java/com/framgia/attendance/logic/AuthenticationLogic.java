package com.framgia.attendance.logic;

import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dao.EmployeeDao;
import com.framgia.attendance.dao.UserAccountDao;
import com.framgia.attendance.entity.Employee;
import com.framgia.attendance.entity.UserAccount;

import static com.framgia.attendance.util.AttendanceConstant.ENCODE_DECODE_KEY;

public class AuthenticationLogic {

    @Binding
    UserAccountDao userAccountDao;

    @Binding
    EmployeeDao employeeDao;

    private UserAccount userAccount;
    private Employee employee;

    public boolean authenticate(String username, String password) {
        boolean authenticated = false;
        UserAccount userAccount =
                userAccountDao.selectUserAccount(username, password);
        authenticated = (userAccount != null);

        if (authenticated) {
            this.userAccount = userAccount;

            // 認証情報から従業員情報を取得
            Integer employeeId = userAccount.getEmployeeId();
            if (null != employeeId) {
                this.employee = employeeDao.getEmployeeById(employeeId, ENCODE_DECODE_KEY);
            }
        }

        return authenticated;
    }

}
