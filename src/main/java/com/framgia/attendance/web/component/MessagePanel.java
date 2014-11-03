package com.framgia.attendance.web.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;

public class MessagePanel extends Panel{
    private static final long serialVersionUID = 1L;
    private MultiLineLabel messageLabel;
    
    public MessagePanel(String id, final ModalWindow modal, String message) {
        super(id);

        MultiLineLabel messageLabel = new MultiLineLabel("message", message);
        AjaxLink<Void> okLink = new AjaxLink<Void>("okLink") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                modal.close(target);
            }
        };
        messageLabel.setEscapeModelStrings(false);
        add(messageLabel);
        add(okLink);
    }

    public void setMessage(String message){
        messageLabel.setDefaultModelObject(message);
    }
}
