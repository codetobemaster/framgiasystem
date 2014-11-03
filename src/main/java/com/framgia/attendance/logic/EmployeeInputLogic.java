package com.framgia.attendance.logic;

import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.Component;

import com.framgia.attendance.dao.EmployeeDao;

@Component
public class EmployeeInputLogic {

    @Binding
    EmployeeDao employeeDao;
public EmployeeInputLogic() {
}
}
