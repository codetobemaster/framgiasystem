package com.framgia.attendance.web.top;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dao.TimeInTimeOutDao;
import com.framgia.attendance.web.attendance.AttendancePage;
import com.framgia.attendance.web.template.BaseWebPage;
import com.framgia.attendance.web.template.WebTemplatePage;

public class HomePage extends WebTemplatePage {

	private static final long serialVersionUID = 1L;

	private String name;
	@Binding
	TimeInTimeOutDao timeIntimOut;

	public HomePage(PageParameters parameters) {
		super(parameters);
        add(new Label("title", new PropertyModel<String>(this, "pageTitle")));
		add(new FeedbackPanel("feedback"));
		WebMarkupContainer div = new WebMarkupContainer("container");
		add(div);
		div.add(new Label("message", "お疲れさまです。"));

		div.add(new Link<Void>("link") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				 setResponsePage(AttendancePage.class);
			}
		});

		Form<Void> form = new Form<>("form");
		add(form);
		form.add(new TextField<>("name", new PropertyModel<>(this, "name"))
				.setRequired(true));
		form.add(new Button("submitButton") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
			}
		});

		add(new Label("result", new PropertyModel<>(this, "name")));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPageTitle() {
		return "HomePage";
	}

}
