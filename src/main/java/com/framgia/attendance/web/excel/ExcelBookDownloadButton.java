package com.framgia.attendance.web.excel;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.IResourceStream;

public class ExcelBookDownloadButton extends Button {
    private static final long serialVersionUID = 1L;

    private final String fileName;

    private IModel<ExcelBook> bookModel;

    public ExcelBookDownloadButton(String id, String fileName,
            IModel<ExcelBook> model) {
        super(id);
        this.fileName = fileName;
        this.bookModel = model;
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        if (bookModel != null) {
            bookModel.detach();
        }
    }

    @Override
    public void onSubmit() {
        IResourceStream resource =
            new AsposeExcelBookResourceStream(bookModel.getObject());
        ResourceStreamRequestHandler handler =
            new ResourceStreamRequestHandler(resource);
        handler.setFileName(getFileName());
        getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
    }

    protected String getFileName() {
        return fileName;
    }
}
