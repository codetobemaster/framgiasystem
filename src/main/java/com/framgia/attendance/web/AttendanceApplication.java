package com.framgia.attendance.web;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.framgia.attendance.web.top.HomePage;
import com.framgia.attendance.web.top.LoginPage;

public class AttendanceApplication extends AuthenticatedWebApplication {

	// private final static Logger logger =
	// LoggerFactory.getLogger(AttendanceApplication.class);


	@Override
	protected void init() {
		super.init();
		authorize();
		getDebugSettings().setOutputComponentPath(true);
		getDebugSettings().setAjaxDebugModeEnabled(false);
		// enable logger
		Application.get().getRequestLoggerSettings()
				.setRequestLoggerEnabled(true);

		// mount pages
		mountPages();

		try {
			new com.aspose.cells.License().setLicense(getClass()
					.getClassLoader().getResourceAsStream(
							"Aspose.Total.Java.lic"));
		} catch (Exception e) {
			// logger.error("Aspose load error.", e);
		}

	}

	private void authorize() {
/*		MetaDataRoleAuthorizationStrategy.authorize(
				ApproverTimesheetListPage.class, AttendanceRoles.APPROVER);
		MetaDataRoleAuthorizationStrategy.authorize(
				ActiveEmployeeListPage.class, AttendanceRoles.USER);
		MetaDataRoleAuthorizationStrategy.authorize(UserAccountPage.class,
				Roles.USER);
		MetaDataRoleAuthorizationStrategy.authorize(EmployeeInputPage.class,
				Roles.USER);
		MetaDataRoleAuthorizationStrategy.authorize(
				AdminEditEmployeePage.class, AttendanceRoles.ADMIN);

		MetaDataRoleAuthorizationStrategy.authorize(AdminEditUserPage.class,
				Roles.ADMIN);
		MetaDataRoleAuthorizationStrategy.authorize(AdminToolsPage.class,
				Roles.ADMIN);
		MetaDataRoleAuthorizationStrategy.authorize(TimesheetListPage.class,
				Roles.ADMIN);
		MetaDataRoleAuthorizationStrategy.authorize(UserListPage.class,
				Roles.ADMIN);
		MetaDataRoleAuthorizationStrategy.authorize(EmployeeListPage.class,
				Roles.ADMIN);
		MetaDataRoleAuthorizationStrategy.authorize(GroupManageListPage.class,
				Roles.ADMIN);
		MetaDataRoleAuthorizationStrategy.authorize(
				GroupManageUpdatePage.class, AttendanceRoles.ADMIN);*/

	}

	private void mountPages() {
		mountPage("login", LoginPage.class);
	/*	mountPage("employees", ActiveEmployeeListPage.class);
		mountPage("employees/input", EmployeeInputPage.class);
		mountPage("userAccount", UserAccountPage.class);
		mountPage("admin/editEmployee", AdminEditEmployeePage.class);
		mountPage("admin/editUser", AdminEditUserPage.class);
		mountPage("admin/tools", AdminToolsPage.class);
		mountPage("admin/timeSheetList", TimesheetListPage.class);
		mountPage("admin/userList", UserListPage.class);
		mountPage("admin/employeeList", EmployeeListPage.class);
		mountPage("approver/timeSheetList", ApproverTimesheetListPage.class);
		mountPage("admin/groupList", GroupManageListPage.class);
		mountPage("admin/editGroup", GroupManageUpdatePage.class);
        mountPage("admin/rankList", RankListPage.class);
		mountPage("reset", ResetPasswordPage.class);*/
//		mountPage("forget", ForgetPasswordPage.class);
		mountPage("home", HomePage.class);
		
		
		
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return AttendanceWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new AttendanceWebSession(request);
	}

}
