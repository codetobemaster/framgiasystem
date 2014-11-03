package com.framgia.attendance.web.top;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dao.EmployeeDao;
import com.framgia.attendance.dao.UserAccountDao;
import com.framgia.attendance.entity.Employee;
import com.framgia.attendance.entity.UserAccount;
import com.framgia.attendance.logic.EmployeeInputLogic;
import com.framgia.attendance.web.AttendanceWebSession;
import com.framgia.attendance.web.template.BaseWebPage;

public class LoginPage extends WebPage {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private final int PASSWORD_EXPIRE_DAYS = 90;
    private final String USER_GUEST = "GUEST";
    private boolean authResult = false; 
    
    @Binding
    UserAccountDao userAccDao;
    
    @Binding
    EmployeeDao employeeDao;
    
    @Binding
    EmployeeInputLogic employeeInputLogic;

    public LoginPage() {
        super();
        add(new Image("logo", BaseWebPage.UB_LOGO));
        // feeback panel
        add(new FeedbackPanel("feedback"));

        StatelessForm form = new StatelessForm("loginForm") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                if(username.toUpperCase().equals(USER_GUEST)){
                    authenticate();
                }else{
                    UserAccount userAcc = userAccDao.getUserAccount(
                            username, password);
                    //Username/Password wrong
                    if(userAcc == null){
                        error(getString("loginForm.invalidCredentials"));
                    }else if(userAcc.getDeleteFlag() == 1){
                        error(getString("loginForm.error.userDeleted"));
                    } else {
                        Employee currentEmployee = employeeDao.getActiveEmployee(
                                userAcc.getEmployeeId(), new Date());
                        if(currentEmployee == null){
                            error(getString("loginForm.error.userDeleted"));
                        } else if(currentEmployee.getEmploymentDate().compareTo(new Date()) > 0){
                            error(getString("loginForm.expiredTime"));
                        } else if(haveToUpdatePassword(userAcc)){
//                            setResponsePage(new ResetPasswordPage(userAcc));
                        } else{
                            authenticate();
                        }
                    }
                }
            }
        };

        form.setDefaultModel(new CompoundPropertyModel(this));
        form.add(new RequiredTextField("username"));
        form.add(new PasswordTextField("password"));
        form.add(new Link("forgetLink") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                setResponsePage(ForgetPasswordPage.class);
            }
        });
        add(form);
    }
    
    private boolean haveToUpdatePassword(UserAccount userAcc){
        Date rsDate = userAcc.getUpdatePasswordDate();
        if (rsDate == null) 
            return true;
        Calendar now = Calendar.getInstance();
        Calendar rsDate90Days = Calendar.getInstance();
        rsDate90Days.setTime(rsDate);
        rsDate90Days.add(Calendar.DAY_OF_YEAR, PASSWORD_EXPIRE_DAYS);
        return (rsDate90Days.compareTo(now) <= 0);
    }
    
    private void authenticate(){
        authResult = AuthenticatedWebSession.get().signIn(
                username, password);

        if (authResult) {
            continueToOriginalDestination();
            AttendanceWebSession attendanceWebSession = (AttendanceWebSession) AttendanceWebSession
                    .get();
            Employee currentEmployee = attendanceWebSession
                    .getCurrentEmployee();
            
            //Reset PayVacationLeft process
            UserAccount userAccount = attendanceWebSession.getCurrentUserAccount();
//            employeeInputLogic.resetPayVacationLeft(userAccount);
            
            if (null == currentEmployee) {
                // 従業員情報が存在しない場合は従業員一覧画面へ
//                setResponsePage(ActiveEmployeeListPage.class);/
            } else {
                // 従業員情報が存在する場合は月次入力画面へ
//                setResponsePage(EmployeeInputPage.class);
            }
        } else {
            error(getString("loginForm.invalidCredentials"));
        }
    }
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(BaseWebPage.BOOTSTRAP_CSS));
        response.render(CssHeaderItem.forReference(BaseWebPage.ATTENDANCE_CSS));
        response.render(JavaScriptHeaderItem
                .forReference(BaseWebPage.BOOTSTRAP_JS));

    }

}
