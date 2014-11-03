package com.framgia.attendance.web.excel;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.IResourceStream;

public class ExcelBookDownloadLink extends Link<ExcelBook> {
    private static final long serialVersionUID = 1L;

    private final String fileName;

    public ExcelBookDownloadLink(String id, String fileName,
            IModel<ExcelBook> model) {
        super(id, model);
        this.fileName = fileName;
    }

    public ExcelBookDownloadLink(String id, String fileName, int[] widths,
            IModel<ExcelBook> model) {
        super(id, model);
        this.fileName = fileName;
    }

    @Override
    public void onClick() {
        IResourceStream resource =
                new AsposeExcelBookResourceStream(getModelObject());

        ResourceStreamRequestHandler target =
                new ResourceStreamRequestHandler(resource);
        target.setFileName(getFileName());
        
        getRequestCycle().scheduleRequestHandlerAfterCurrent(target);
    }

    protected String getFileName() {
        String encoded = fileName;
        try {
            encoded = URLEncoder.encode(fileName, "UTF-8").replace("+", "");
        } catch (UnsupportedEncodingException e) {
        }
        return encoded;
    }
    
    
    
    
}
