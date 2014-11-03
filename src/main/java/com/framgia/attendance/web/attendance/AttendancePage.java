package com.framgia.attendance.web.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.seasar.framework.container.annotation.tiger.Binding;

import com.framgia.attendance.dto.TimeInTimeOutDto;
import com.framgia.attendance.entity.TimeInTimeOut;
import com.framgia.attendance.logic.AttendanceLogic;
import com.framgia.attendance.web.template.BaseWebPage;

public class AttendancePage extends BaseWebPage {

    private static final long serialVersionUID = 1L;

    private String name;

    private PageableListView<TimeInTimeOutDto> listViewTimeIntimeOut;
    private List<TimeInTimeOutDto> listTimeInTimeOut;
    @Binding
    AttendanceLogic attendanceLogic;

    public AttendancePage(PageParameters parameters) {
        super(parameters);
        listTimeInTimeOut =
                attendanceLogic.getListTimeInTimeOut(new Date(), new Date());
        add(new FeedbackPanel("feedback"));
        WebMarkupContainer div = new WebMarkupContainer("container");
        add(div);
        Form<Void> form = new Form<>("form");
        add(form);
        form.add(new TextField<String>("name", new PropertyModel<String>(this,
                "name")).setRequired(true));
        form.add(new Button("submitButton") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit() {

            }
        });

        // add listView
        listViewTimeIntimeOut =
                new PageableListView<TimeInTimeOutDto>("listViewTimeInOut",
                        listTimeInTimeOut, 50) {
                    private static final long serialVersionUID = 1L;
                    @Override
                    protected void populateItem(ListItem<TimeInTimeOutDto> item) {
                        final TimeInTimeOutDto timeInOut = item.getModelObject();
                        item.setDefaultModel(new CompoundPropertyModel<TimeInTimeOutDto>(
                                timeInOut));
                        item.add(new Label("WorkingDay"));
                        item.add(new Label("EmployeeID"));
                        item.add(new Label("EmployeeName"));
                        item.add(new Label("TimeIn"));
                        item.add(new Label("TimeOut"));
                    }
                };
        div.add(listViewTimeIntimeOut);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPageTitle() {
        return "Framgia Attendance";
    }

}
