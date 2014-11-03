package com.framgia.attendance.web.template;

import java.util.Locale;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dao.GroupDao;
import com.framgia.attendance.web.AttendanceApplication;
import com.framgia.attendance.web.AttendanceWebSession;

public class WebTemplatePage extends WebPage {

    private static final long serialVersionUID = 1L;

    protected PageParameters parameters;
    @Binding
    private GroupDao groupEmpDao;

    @Override
    protected void onConfigure() {
        getSession().bind();

        // 言語
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
    }

    public WebTemplatePage() {
        super();
    }

    public WebTemplatePage(PageParameters parameters) {
        super(parameters);

        if (parameters == null) {
            this.parameters = new PageParameters();
        } else {
            this.parameters = parameters;
        }
    }

    public static final CssResourceReference BOOTSTRAP_CSS =
            new CssResourceReference(AttendanceApplication.class,
                    "css/bootstrap.css");

    public static final CssResourceReference ATTENDANCE_CSS =
            new CssResourceReference(AttendanceApplication.class,
                    "css/attendance.css");

    public static final JQueryPluginResourceReference BOOTSTRAP_JS =
            new JQueryPluginResourceReference(AttendanceApplication.class,
                    "js/bootstrap.js");
    public static final JQueryPluginResourceReference CUSTOM_JS =
            new JQueryPluginResourceReference(AttendanceApplication.class,
                    "js/custom.js");

    public static final PackageResourceReference UB_LOGO =
            new PackageResourceReference(AttendanceApplication.class,
                    "images/lgo_uzabase_01.gif");
    public static final CssResourceReference DATEPICKER_CSS =
            new CssResourceReference(AttendanceApplication.class,
                    "css/datepicker.css");
    public static final JQueryPluginResourceReference DATEPICKER_JS =
            new JQueryPluginResourceReference(AttendanceApplication.class,
                    "js/bootstrap-datepicker.js");

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));
        response.render(CssHeaderItem.forReference(DATEPICKER_CSS));
        response.render(CssHeaderItem.forReference(ATTENDANCE_CSS));

        response.render(JavaScriptHeaderItem.forReference(BOOTSTRAP_JS));
        response.render(JavaScriptHeaderItem.forReference(DATEPICKER_JS));
        response.render(JavaScriptHeaderItem.forReference(BaseWebPage.CUSTOM_JS));
    }

    /**
     * ページタイトルを取得するメソッド <title></title>の部分です
     * 
     * @return タイトル
     */
    public String getPageTitle() {
        return "Framgia OS System";
    }

    @Override
    public AttendanceWebSession getSession() {
        return (AttendanceWebSession) super.getSession();
    }

}
