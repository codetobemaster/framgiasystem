package com.framgia.attendance.web.component.link;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;
import org.karatachi.wicket.grid.Cells;
import org.karatachi.wicket.grid.ICell;

public class EncodingSelectableExcelResourceStream extends AbstractResourceStream {
    private static final long serialVersionUID = 1L;

    private List<Cells> cellsList;

    private String encoding;

    public EncodingSelectableExcelResourceStream(Cells cells, String encoding) {
        this.cellsList = Collections.singletonList(cells);
        this.encoding = encoding;
    }

    public EncodingSelectableExcelResourceStream(List<Cells> cells,
            String encoding) {
        this.cellsList = cells;
        this.encoding = encoding;
    }

    @Override
    public String getContentType() {
        return "application/vnd.ms-excel";
    }

    @Override
    public Bytes length() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws ResourceStreamNotFoundException {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            WorkbookSettings ws = new WorkbookSettings();
            ws.setLocale(Locale.JAPAN);
            ws.setEncoding(encoding);

            WritableWorkbook workbook = Workbook.createWorkbook(os, ws);

            final ListIterator<Cells> it = cellsList.listIterator();
            int sheetNo = 0;
            int rowsInSheet = -1;
            while (it.hasNext()) {
                WritableSheet sheet = createSheet(workbook, sheetNo++);
                setupSheet(sheet);

                final Cells cells = it.next();
                int rows = cells.getRows();
                int cols = cells.getCols();
				rowsInSheet = 0;
                
                for (int r = 0; r < rows; ++r) {
                    rowsInSheet++;
                    if (rowsInSheet >= 65536) {
                        rowsInSheet = 0;
                        sheet = createSheet(workbook, sheetNo++);
                        setupSheet(sheet);
                    }
                    for (int c = 0; c < cols; ++c) {
                        ICell cell = cells.getCell(r + 1, c + 1);
                        if (cell == null || cell.getValue() == null) {
                            continue;
                        }

                        Object obj = cell.getValue();

                        WritableCell w;
                        if (obj instanceof Number) {
                            w =
                                    new jxl.write.Number(c, rowsInSheet,
                                            ((Number) obj).doubleValue());
                        } else if (obj instanceof Date) {
                            w =
                                    new DateTime(c, rowsInSheet, (Date) obj,
                                            new WritableCellFormat(
                                                    DateFormats.DEFAULT));
                        } else {
                            w = new Label(c, rowsInSheet, obj.toString());
                        }

                        setupCell(cell, w);

                        sheet.addCell(w);
                    }
                }
            }
            workbook.write();
            workbook.close();
            os.close();

            return new ByteArrayInputStream(os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceStreamNotFoundException(e);
        }
    }

    protected WritableSheet createSheet(WritableWorkbook workbook, int index) {
        return workbook.createSheet("Sheet" + (index + 1), index);
    }

    protected void setupSheet(WritableSheet w) {
    }

    protected void setupCell(ICell cell, WritableCell w) throws WriteException {
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public Time lastModifiedTime() {
        return Time.now();
    }
}
