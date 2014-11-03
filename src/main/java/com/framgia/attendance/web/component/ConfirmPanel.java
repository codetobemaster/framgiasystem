package com.framgia.attendance.web.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Matej Knopp
 */
public abstract class ConfirmPanel extends Panel {

    private static final long serialVersionUID = 1L;
    /**
     * @param id
     */
    private String message;
    private Label lable;

    private WebMarkupContainer container;

    public ConfirmPanel(String id, String message) {
        super(id);
        this.message = message;
        container = new WebMarkupContainer("container");
        add(container);
        container.setOutputMarkupPlaceholderTag(true);
        container.add(lable = new Label("message", this.message));
        lable.setEscapeModelStrings(false);

        lable.setOutputMarkupPlaceholderTag(true);
        add(new AjaxLink<Void>("closeOK") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                onOk(target);
            }
        });

        add(new AjaxLink<Void>("closeCancel") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                onCancel(target);
            }
        });
    }

    public abstract void onCancel(AjaxRequestTarget target);

    public abstract void onOk(AjaxRequestTarget target);

    public String getMessage() {
        return message;
    }

    public void setMessage(String message, AjaxRequestTarget target) {
        this.message = message;
        lable = new Label("message", this.message);
        lable.setEscapeModelStrings(false);
        container.addOrReplace(lable);
        target.add(container);
    }
}
