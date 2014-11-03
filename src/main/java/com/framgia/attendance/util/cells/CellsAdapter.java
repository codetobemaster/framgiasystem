package com.framgia.attendance.util.cells;

import java.io.Serializable;
import java.util.Comparator;

import org.karatachi.wicket.grid.Cells;
import org.karatachi.wicket.grid.ICell;

/**
 * CellsのAdapterパターンクラス
 * このクラスの主な目的は、Cellsの添字が1開始となることを回避する目的です
 */
public class CellsAdapter implements Serializable {
    private static final long serialVersionUID = 1L;

    private Cells cells;

    public CellsAdapter() {
        cells = new Cells();
    }

    public CellsAdapter(final Cells cells) {
        this.cells = cells;
    }

    public Cells getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return cells.toString();
    }

    public void pivot() {
        cells.pivot();
    }

    public void clear() {
        cells.clear();
    }

    public int getRows() {
        return cells.getRows();
    }

    public int getCols() {
        return cells.getCols();
    }

    public void sortRow(final int row, final int col, final boolean ascend,
            final Comparator<ICell> comparator) {
        cells.sortRow(row + 1, col + 1, ascend, comparator);
    }

    public void insertRow(final int row) {
        cells.insertRow(row + 1);
    }

    public void removeRow(final int row) {
        cells.removeRow(row + 1);
    }

    public void insertCol(final int col) {
        cells.insertCol(col + 1);
    }

    public void removeCol(final int col) {
        cells.removeCol(col + 1);
    }

    public ICell getCell(final int row, final int col) {
        return cells.getCell(row + 1, col + 1);
    }

    public void putCell(final int row, final int col, final ICell cell) {
        cells.putCell(row + 1, col + 1, cell);
    }

    public Object getValue(final int row, final int col) {
        return cells.getValue(row + 1, col + 1);
    }

    public ICell setValue(final int row, final int col, final Object value) {
        return cells.setValue(row + 1, col + 1, value);
    }
}
