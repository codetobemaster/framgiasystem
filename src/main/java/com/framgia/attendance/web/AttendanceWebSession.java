package com.framgia.attendance.web;

import static com.framgia.attendance.util.AttendanceConstant.ENCODE_DECODE_KEY;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dao.EmployeeDao;
import com.framgia.attendance.dao.GroupDao;
import com.framgia.attendance.dao.UserAccountDao;
import com.framgia.attendance.entity.Employee;
import com.framgia.attendance.entity.UserAccount;
import com.framgia.attendance.util.AttendanceRoles;
import com.framgia.attendance.util.UserAccountType;

public class AttendanceWebSession extends AuthenticatedWebSession implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Binding
//	private AuthenticationLoggerLogic authenticationLoggerLogic;
	private UserAccount currentUserAccount;
	private Employee currentEmployee;

	public AttendanceWebSession(Request request) {
		super(request);
	}
	
	@Override
	public boolean authenticate(String username, String password) {
		boolean authenticated = false;

		UserAccountDao userAccountDao = SingletonS2Container
				.getComponent(UserAccountDao.class);

		UserAccount userAccount = userAccountDao.selectUserAccount(username,
				password);
//		AuthenticationLoggerLogic authenticationLoggerLogic = SingletonS2Container
//	                .getComponent(AuthenticationLoggerLogic.class);
		authenticated = (userAccount != null);

		if (authenticated) {
			this.currentUserAccount = userAccount;
			// 認証情報から従業員情報を取得
			Integer employeeId = userAccount.getEmployeeId();
			if (null != employeeId) {
				EmployeeDao employeeDao = SingletonS2Container
						.getComponent(EmployeeDao.class);
				this.currentEmployee = employeeDao.getEmployeeById(employeeId, ENCODE_DECODE_KEY);
			}
//		    authenticationLoggerLogic.writeAuthenticationLog(
//	                userAccount.getUserId(),
//	                "login");
		}

		return authenticated;
	}

	public UserAccount getCurrentUserAccount() {
		return this.currentUserAccount;
	}

	public void setCurrentUserAccount(UserAccount userAccount) {
		this.currentUserAccount = userAccount;
	}

	public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

	public void setCurrentEmployee(Employee employee) {
		this.currentEmployee = employee;
	}

	@Override
	public Roles getRoles() {
		Roles roles = new Roles();
		if (isSignedIn()) {
			roles.add(Roles.USER);
			if (currentUserAccount.getUserType() == UserAccountType.ADMIN
					.getUserType()) {
				roles.add(Roles.ADMIN);
			}
			GroupDao groupDao = SingletonS2Container
					.getComponent(GroupDao.class);
			List<Integer> groupsByApprover = groupDao
					.listApproverGroups(currentUserAccount.getEmployeeId());
			if (groupsByApprover.size() > 0) {
				roles.add(AttendanceRoles.APPROVER);
			}

		}
		return roles;
	}

}
