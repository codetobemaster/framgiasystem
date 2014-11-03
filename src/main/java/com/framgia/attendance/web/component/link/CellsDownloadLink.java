package com.framgia.attendance.web.component.link;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.IResourceStream;
import org.karatachi.wicket.grid.Cells;

public class CellsDownloadLink extends Link<Cells> {
    private static final long serialVersionUID = 1L;

    private final String encoding;
    private final String fileName;
    private final int[] widths;

    public CellsDownloadLink(String id, String fileName, String encoding,
            IModel<Cells> model) {
        super(id, model);
        this.encoding = encoding;
        this.fileName = fileName;
        this.widths = null;
    }

    public CellsDownloadLink(String id, String fileName, IModel<Cells> model) {
        super(id, model);
        this.encoding = "Windows-31J";
        this.fileName = fileName;
        this.widths = null;
    }

    public CellsDownloadLink(String id, String fileName, int[] widths,
            IModel<Cells> model) {
        super(id, model);
        this.encoding = "Windows-31J";
        this.fileName = fileName;
        this.widths = widths;
    }

    public CellsDownloadLink(String id, String fileName, String encoding,
            int[] widths, IModel<Cells> model) {
        super(id, model);
        this.encoding = encoding;
        this.fileName = fileName;
        this.widths = widths;
    }

    @Override
    public void onClick() {
        IResourceStream resource =
                new CellsDownloadStream(getModelObject(), widths, encoding);

        ResourceStreamRequestHandler handler =
                new ResourceStreamRequestHandler(resource, fileName);
        getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
    }
}
