package com.framgia.attendance.web.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableCellFormat;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.karatachi.wicket.grid.Cells;
import org.karatachi.wicket.grid.ICell;

public class ExcelBook {

    public boolean isPrintLogo;
    public String logoHyperLinkURI;
    public InputStream defaultXL;
    public boolean isDeleteFirstSheet;

    private List<ExcelSheet> sheets;

    public ExcelBook() {
        this(false, null, null);
    }

    public ExcelBook(boolean isPrintLogo) {
        this(isPrintLogo, null, null);
    }

    public ExcelBook(boolean isPrintLogo, String logoHyperLinkURI) {
        this(isPrintLogo, logoHyperLinkURI, null);
    }

    public ExcelBook(boolean isPrintLogo, String logoHyperLinkURI,
            InputStream defaultXL) {
        sheets = new ArrayList<ExcelSheet>();
        this.isPrintLogo = isPrintLogo;
        this.logoHyperLinkURI = logoHyperLinkURI;

        if (defaultXL != null) {
            this.defaultXL = defaultXL;
            this.isDeleteFirstSheet = false;
        } else {
            this.defaultXL =
                    getClass().getResourceAsStream("default_template_jp.xls");
            this.isDeleteFirstSheet = true;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(
                "isPrintLogo", isPrintLogo).append("logoHyperLinkURI",
                logoHyperLinkURI).append("sheets", sheets).toString();
    }

    public ExcelBook setPrintLogo(boolean isPrintLogo, String logoHyperLinkURI) {
        this.isPrintLogo = isPrintLogo;
        this.logoHyperLinkURI = logoHyperLinkURI;
        return this;
    }

    public InputStream getLogoImage() {
        return getClass().getResourceAsStream("speeda.png");
    }

    public int getLogoHeight() {
        return 127;
    }

    public int getLogoWidth() {
        return 120;
    }

    public ExcelSheet createSheet(String sheetName) {
        if (sheetName == null) {
            throw new NullPointerException("sheetName is must setting.");
        }
        validateSheetName(sheetName);

        ExcelSheet sheet = new ExcelSheet(sheetName);
        sheets.add(sheet);
        return sheet;
    }

    public ExcelSheet createSheet(String sheetName, Cells cells) {
        if (sheetName == null) {
            throw new NullPointerException("sheetName is must setting.");
        }
        sheetName = escapeSheetName(sheetName);
        if (sheetName.length() > 31) {// Excelの仕様で31文字までのため
            sheetName = sheetName.substring(0, 31);
        }
        validateSheetName(sheetName);

        ExcelSheet sheet = new ExcelSheet(sheetName, cells);
        sheets.add(sheet);
        return sheet;
    }

    private String escapeSheetName(String sheetName) {
        return sheetName.replaceAll(":", "").replaceAll("\\\\", "").replaceAll(
                "\\?", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(
                "/", "").replaceAll("\\*", "");
    }

    private void validateSheetName(String sheetName) {
        for (ExcelSheet sheet : sheets) {
            if (sheet.sheetName.equals(sheetName)) {
                throw new InvalidSheetNameException();
            }
        }
    }

    /**
     * Excelシートを追加するメソッド
     * 
     * @param excelSheet
     */
    public void addSheet(final ExcelSheet excelSheet) {
        sheets.add(excelSheet);
    }

    public ExcelSheet getSheet(String sheetName) {
        for (ExcelSheet sheet : sheets) {
            if (sheet.sheetName.equals(sheetName)) {
                return sheet;
            }
        }
        return null;
    }

    public void removeSheet(String sheetName) {
        ExcelSheet target = null;
        for (ExcelSheet sheet : sheets) {
            if (sheet.sheetName.equals(sheetName)) {
                target = sheet;
                break;
            }
        }
        if (target != null) {
            sheets.remove(target);
        }
    }

    public List<ExcelSheet> getSheets() {
        return sheets;
    }

    public List<ExcelSheet> getReverseSheets() {
        List<ExcelSheet> list = new ArrayList<ExcelBook.ExcelSheet>();
        for (int i = sheets.size() - 1; 0 <= i; i--) {
            list.add(sheets.get(i));
        }
        return list;
    }

    public static class ExcelSheet {
        String sheetName;
        Cells cells;
        Map<Integer, Integer> width;
        Map<Integer, Integer> height;
        List<MergeData> merges;
        FreezePane freezePane;

        public ExcelSheet(String sheetName) {
            this.sheetName = sheetName;
            this.cells = null;
            this.freezePane = null;
            setup();
        }

        public ExcelSheet(String sheetName, Cells cells) {
            this.sheetName = sheetName;
            this.cells = cells;
            this.freezePane = null;
            setup();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(
                    "sheetName", sheetName).append("cells", cells).append(
                    "width", width).append("height", height).append("merges",
                    merges).append("freezePane", freezePane).toString();
        }

        private void setup() {
            width = new HashMap<Integer, Integer>();
            height = new HashMap<Integer, Integer>();
            merges = new ArrayList<MergeData>();
        }

        public void setWidth(int column, int size) {
            if (column <= 0) {
                throw new OutOfCellException(
                        "column number start is 1, you set number:" + column);
            }
            width.put(column - 1, size);
        }

        public void setFreezePane(int freezeRow, int freezeCol) {
            setFreezePane(freezeRow, freezeCol, freezeRow, freezeCol);
        }

        public void setFreezePane(int freezeRow, int freezeCol, int visibleRow,
                int visibleCol) {
            if (freezeRow <= 0 || freezeCol <= 0 || visibleRow <= 0
                    || visibleCol <= 0) {
                throw new OutOfCellException(
                        "column or row number start is 1, you set number:"
                                + freezeRow + "," + freezeCol + ","
                                + visibleRow + "," + visibleCol);
            }
            freezePane =
                    new FreezePane(freezeRow, freezeCol, visibleRow, visibleCol);
        }

        public void setHeight(int row, int size) {
            if (row <= 0) {
                throw new OutOfCellException(
                        "row number start is 1, you set number:" + row);
            }
            height.put(row - 1, size);
        }

        public void mergeColumnSpans() {
            for (int r = 1, rows = cells.getRows(); r <= rows; ++r) {
                for (int c = 1, cols = cells.getCols(); c <= cols; ++c) {
                    ICell cell = cells.getCell(r, c);
                    if (cell == null) {
                        continue;
                    }

                    int colspan = cell.getColspan();
                    if (colspan <= 1) {
                        continue;
                    }

                    addMergeColumn(r, c, c + colspan - 1);
                }
            }
        }

        public void addMerge(int startRow, int startCol, int endRow, int endCol) {
            if (startRow <= 0 || startCol <= 0 || endRow <= 0 || endCol <= 0) {
                throw new OutOfCellException(
                        "column or row number start is 1, you set number:"
                                + startRow + "," + startCol + "," + endRow
                                + "," + endCol);
            }
            merges.add(new MergeData(startRow, startCol, endRow, endCol));
        }

        public void addMergeColumn(int row, int startCol, int endCol) {
            if (row <= 0 || startCol <= 0 || endCol <= 0) {
                throw new OutOfCellException(
                        "column or row number start is 1, you set number:"
                                + row + "," + startCol + "," + endCol);
            }
            merges.add(new MergeData(row, startCol, row, endCol));
        }

        public void addMergeRow(int column, int startRow, int endRow) {
            if (startRow <= 0 || endRow <= 0 || column <= 0) {
                throw new OutOfCellException(
                        "column or row number start is 1, you set number:"
                                + column + "," + startRow + "," + endRow);
            }
            merges.add(new MergeData(startRow, column, endRow, column));
        }

        public void addMergeList(List<MergeData> mergeList) {
            if (mergeList == null) {
                return;
            }
            for (MergeData mergeData : mergeList) {
                if (mergeData.startRow < 0 || mergeData.startCol < 0
                        || mergeData.endRow < 0 || mergeData.endCol < 0) {
                    throw new OutOfCellException(
                            "column or row number start is 1, you set number:"
                                    + mergeData.startRow + ","
                                    + mergeData.startCol + ","
                                    + mergeData.endRow + "," + mergeData.endCol);
                }
            }
            merges.addAll(mergeList);
        }

        public static class MergeData {
            public int startRow;
            public int startCol;
            public int endRow;
            public int endCol;

            public MergeData(int startRow, int startCol, int endRow, int endCol) {
                this.startRow = startRow - 1;
                this.startCol = startCol - 1;
                this.endRow = endRow - 1;
                this.endCol = endCol - 1;
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this,
                        ToStringStyle.SHORT_PREFIX_STYLE).append("startRow",
                        startRow).append("startCol", startCol).append("endRow",
                        endRow).append("endCol", endCol).toString();
            }
        }

        public static class FreezePane {
            public int freezeRow;
            public int freezeCol;
            public int visibleRow;
            public int visibleCol;

            public FreezePane(int freezeRow, int freezeCol, int visibleRow,
                    int visibleCol) {
                this.freezeRow = freezeRow - 1;
                this.freezeCol = freezeCol - 1;
                this.visibleRow = visibleRow - 1;
                this.visibleCol = visibleCol - 1;
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this,
                        ToStringStyle.SHORT_PREFIX_STYLE).append("freezeRow",
                        freezeRow).append("freezeCol", freezeCol).append(
                        "visibleRow", visibleRow).append("visibleCol",
                        visibleCol).toString();
            }
        }

        public static class CellsFormat {
            public WritableCellFormat format;
            public int r;
            public int c;

            public CellsFormat(int r, int c, WritableCellFormat format) {
                this.r = r;
                this.c = c;
                this.format = format;
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this,
                        ToStringStyle.SHORT_PREFIX_STYLE).append("r", r).append(
                        "c", c).toString();
            }
        }
    }

    public static class InvalidSheetNameException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public InvalidSheetNameException() {
        }

        public InvalidSheetNameException(String message) {
            super(message);
        }
    }

    public static class OutOfCellException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public OutOfCellException(String message) {
            super(message);
        }
    }
}
