package com.framgia.attendance.web.component;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public class MessageDialog extends ModalWindow{

    private static final long serialVersionUID = 1L;
    private MessagePanel messagePanel;
    public MessageDialog(String id) {
        super(id);
        setInitialHeight(130);
        setInitialWidth(400);
        setResizable(false);
    }

    public void setMessage(String message){
        if (messagePanel == null){
            MessagePanel messagePanel = new MessagePanel(this.getContentId(), this, message);
            setContent(messagePanel);
        } else {
            messagePanel.setMessage(message);
        }
        
    }
}
