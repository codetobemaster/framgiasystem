package com.framgia.attendance.web.template;

import java.util.Locale;
import java.util.MissingResourceException;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dao.GroupDao;
import com.framgia.attendance.util.UserAccountType;
import com.framgia.attendance.web.AttendanceApplication;
import com.framgia.attendance.web.AttendanceWebSession;
import com.framgia.attendance.web.top.LoginPage;

public class BaseWebPage extends WebPage {

	private static final long serialVersionUID = 1L;

	protected PageParameters parameters;
	@Binding
	private GroupDao groupEmpDao;

	@Override
	protected void onConfigure() {
		getSession().bind();

		// if user is not signed in, redirect him to sign in page
		if (!getSession().isSignedIn()) {
			// AttendanceApplication app = (AttendanceApplication) Application
			// .get();
			// app.restartResponseAtSignInPage();
		}

		// Admin Tools link
		boolean isAdmin = false;
		if (getSession().getCurrentUserAccount() != null) {
			isAdmin = getSession().getCurrentUserAccount().getUserType() == UserAccountType.ADMIN
					.ordinal();
		}
		getSession().setLocale(getUserAccountLocale());
	}

	private Locale getUserAccountLocale() {
		if (getSession().getCurrentUserAccount() != null) {
			if (getSession().getCurrentUserAccount().getDefaultLanguage() == 0) {
				return Locale.JAPAN;
			} else {
				return Locale.ENGLISH;
			}
		}
		return getLocale();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		// ページタイトル
		add(new Label("title", new PropertyModel<String>(this, "pageTitle")));

		// トップページへのリンク
		add(new BookmarkablePageLink<>("homePageLink", Application.get()
				.getHomePage()));

		// ユーザ名
		String userName = "";
		if (getSession().getCurrentEmployee() != null) {
			// 従業員情報が存在する場合は漢字のフルネーム

			userName = getSession().getCurrentEmployee()
					.getFullNameKanjiWithNumber();
		} else if (getSession().getCurrentUserAccount() != null) {
			// 従業員情報が存在しない場合はユーザ名
			userName = getSession().getCurrentUserAccount().getUsername();
		}
		add(new Label("userName", userName));

		// ログアウト
		add(new Link("logout") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				AuthenticatedWebSession.get().invalidate();
				setResponsePage(LoginPage.class);
			}
		});
	}

	public BaseWebPage() {
		super();
	}

	public BaseWebPage(PageParameters parameters) {
		super(parameters);

		if (parameters == null) {
			this.parameters = new PageParameters();
		} else {
			this.parameters = parameters;
		}
	}

	public static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(
			AttendanceApplication.class, "css/bootstrap.css");
	public static final CssResourceReference DATEPICKER_CSS = new CssResourceReference(
			AttendanceApplication.class, "css/datepicker.css");

	public static final CssResourceReference ATTENDANCE_CSS = new CssResourceReference(
			AttendanceApplication.class, "css/attendance.css");

	public static final JQueryPluginResourceReference BOOTSTRAP_JS = new JQueryPluginResourceReference(
			AttendanceApplication.class, "js/bootstrap.js");
	public static final JQueryPluginResourceReference DATEPICKER_JS = new JQueryPluginResourceReference(
			AttendanceApplication.class, "js/bootstrap-datepicker.js");
	public static final JQueryPluginResourceReference JQUERY_JS = new JQueryPluginResourceReference(
			AttendanceApplication.class, "jquery/jquery-1.10.2.js");

	public static final JQueryPluginResourceReference CUSTOM_JS = new JQueryPluginResourceReference(
			AttendanceApplication.class, "js/custom.js");

	public static final PackageResourceReference UB_LOGO = new PackageResourceReference(
			AttendanceApplication.class, "images/lgo_uzabase_01.gif");

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		//
		// <link rel="stylesheet" href="bootstrap3/css/bootstrap.css">
		// <link rel="stylesheet" href="assets/css/bootstrap-datepicker.css">
		// <script type="text/javascript" src="jquery/jquery-1.10.2.js">
		// <script type="text/javascript" src="bootstrap3/js/bootstrap.js">

		//
		response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));
		response.render(CssHeaderItem.forReference(DATEPICKER_CSS));
		response.render(CssHeaderItem.forReference(ATTENDANCE_CSS));

		response.render(JavaScriptHeaderItem.forReference(BOOTSTRAP_JS));
		response.render(JavaScriptHeaderItem.forReference(JQUERY_JS));
		response.render(JavaScriptHeaderItem.forReference(DATEPICKER_JS));
		response.render(JavaScriptHeaderItem
				.forReference(BaseWebPage.CUSTOM_JS));
	}

	/**
	 * ページタイトルを取得するメソッド <title></title>の部分です
	 * 
	 * @return タイトル
	 */
	public String getPageTitle() {
		return "UB Attendance";
	}

	@Override
	public AttendanceWebSession getSession() {
		return (AttendanceWebSession) AttendanceWebSession.get();
	}

	protected String getPageID() {
		return this.getClass().getCanonicalName();
	}

	private String getMenuTextFromResource(String key) {
		String value = "";
		try {
			value = getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

}
