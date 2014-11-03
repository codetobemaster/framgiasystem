package com.framgia.attendance.web.excel;

import java.io.Serializable;
import java.util.List;

import org.karatachi.wicket.grid.Cells;

import com.framgia.attendance.util.cells.CellsAdapter;
import com.framgia.attendance.web.excel.ExcelBook.ExcelSheet;
import com.framgia.attendance.web.excel.ExcelBook.ExcelSheet.MergeData;

/**
 * ExcelSheetの範囲指定が1ずれるので作成したAdapter
 */
public class ExcelSheetAdapter implements Serializable {
    private static final long serialVersionUID = 1L;

    private ExcelSheet excelSheet;

    public ExcelSheetAdapter(final ExcelSheet excelSheet) {
        this.excelSheet = excelSheet;
    }

    public ExcelSheetAdapter(final String sheetName) {
        excelSheet = new ExcelSheet(sheetName);
    }

    public ExcelSheetAdapter(final String sheetName, final Cells cells) {
        excelSheet = new ExcelSheet(sheetName, cells);
    }

    public ExcelSheetAdapter(final String sheetName,
            final CellsAdapter cellsAdapter) {
        excelSheet = new ExcelSheet(sheetName, cellsAdapter.getCells());
    }

    public ExcelSheet getExcelSheet() {
        return excelSheet;
    }

    @Override
    public String toString() {
        return excelSheet.toString();
    }

    public void setWidth(final int column, final int size) {
        excelSheet.setWidth(column + 1, size);
    }

    public void setFreezePane(final int freezeRow, final int freezeCol) {
        excelSheet.setFreezePane(freezeRow + 1, freezeCol + 1);
    }

    public void setFreezePane(final int freezeRow, final int freezeCol,
            final int visibleRow, final int visibleCol) {
        excelSheet.setFreezePane(freezeRow + 1, freezeCol + 1, visibleRow + 1,
                visibleCol + 1);
    }

    public void setHeight(final int row, final int size) {
        excelSheet.setHeight(row + 1, size);
    }

    public void mergeColumnSpans() {
        excelSheet.mergeColumnSpans();
    }

    public void addMerge(final int startRow, final int startCol,
            final int endRow, final int endCol) {
        excelSheet.addMerge(startRow + 1, startCol + 1, endRow + 1, endCol + 1);
    }

    public void addMergeColumn(final int row, final int startCol,
            final int endCol) {
        excelSheet.addMergeColumn(row + 1, startCol + 1, endCol + 1);
    }

    public void addMergeRow(final int column, final int startRow,
            final int endRow) {
        excelSheet.addMergeRow(column + 1, startRow + 1, endRow + 1);
    }

    public void addMergeList(final List<MergeData> mergeList) {
        excelSheet.addMergeList(mergeList);
    }
}
