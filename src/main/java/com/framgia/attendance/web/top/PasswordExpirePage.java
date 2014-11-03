package com.framgia.attendance.web.top;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import com.framgia.attendance.web.template.BaseWebPage;
import com.framgia.attendance.web.template.WebTemplatePage;

public class PasswordExpirePage extends WebTemplatePage {

	private static final long serialVersionUID = 1L;

	public PasswordExpirePage() {
		super();
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
