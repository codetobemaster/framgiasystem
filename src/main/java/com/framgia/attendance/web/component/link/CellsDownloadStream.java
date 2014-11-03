package com.framgia.attendance.web.component.link;

import java.util.Date;
import java.util.List;

import jxl.format.Colour;
import jxl.write.DateFormats;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

import org.karatachi.wicket.grid.Cells;
import org.karatachi.wicket.grid.FormattedCell;
import org.karatachi.wicket.grid.ICell;

public class CellsDownloadStream extends EncodingSelectableExcelResourceStream {
    private static final long serialVersionUID = 1L;
    private int[] widths;

    public CellsDownloadStream(Cells cells, int[] widths) {
        super(cells, "Windows-31J");
        this.widths = widths;
    }

    public CellsDownloadStream(Cells cells, int[] widths, String encoding) {
        super(cells, encoding);
        this.widths = widths;
    }

    public CellsDownloadStream(List<Cells> cells, int[] widths) {
        super(cells, "Windows-31J");
        this.widths = widths;
    }
    
    public CellsDownloadStream(List<Cells> cells, int[] widths, String encoding) {
        super(cells, encoding);
        this.widths = widths;
    }
    
    @Override
    protected void setupSheet(WritableSheet w) {
        for (int i = 0; i < 256; i++) {
            if (widths != null && i < widths.length) {
                w.setColumnView(i, widths[i]);
            } else {
                w.setColumnView(i, 12);
            }
        }
    }

    @Override
    protected void setupCell(ICell cell, WritableCell w) throws WriteException {
        WritableCellFormat format;

        if (cell.getValue() instanceof Number) {
            if (cell instanceof FormattedCell
                    && ((FormattedCell) cell).getFormat().matches(
                            "%,\\.[1-9]f.*")) {
                format = new WritableCellFormat(NumberFormats.THOUSANDS_FLOAT);
/*                int num =
                        Integer.parseInt(((FormattedCell) cell).getFormat().charAt(
                                3)
                                + "");
                String formatStr = "#,###";
                if(Math.abs(((Number)cell.getValue()).doubleValue()) < 1) {
                	formatStr = "0";
                }
                if (num != 0) {
                    formatStr += ".";
                }
                for (int i = 0; i < num; i++) {
                    formatStr += "0";
                }
                format = new WritableCellFormat(new NumberFormat(formatStr));
*/
            } else {
                format =
                        new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
            }
        } else if (cell.getValue() instanceof Date) {
            format = new WritableCellFormat(DateFormats.DEFAULT);
        } else {
            format = new WritableCellFormat(NumberFormats.TEXT);
        }

        if (cell.getClassName() == null) {
            ;
        } else if (cell.getClassName().indexOf("h") >= 0
                || cell.getClassName().indexOf("l") >= 0) {
            WritableFont font = new WritableFont(format.getFont());
            font.setBoldStyle(WritableFont.BOLD);
            format.setFont(font);
        } else if(cell.getClassName().equals("grey")) {
        	WritableFont font = new WritableFont(format.getFont());
            font.setBoldStyle(WritableFont.BOLD);
            format.setFont(font);
        	format.setBackground(Colour.GREY_25_PERCENT);	
        } else if (cell.getClassName().equals("rb")) {
        	WritableFont font = new WritableFont(format.getFont());
            font.setBoldStyle(WritableFont.BOLD);
            format.setFont(font);
        	format.setBackground(Colour.ICE_BLUE);
        } else if (cell.getClassName().equals("r")) {
        	WritableFont font = new WritableFont(format.getFont());
            font.setBoldStyle(WritableFont.NO_BOLD);
            format.setFont(font);
        	format.setBackground(Colour.ICE_BLUE);
        }
        
        if (cell.getIndent() != 0) {
            format.setIndentation(cell.getIndent() / 20);
        }

        w.setCellFormat(format);
    }
}
