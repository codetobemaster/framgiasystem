package com.framgia.attendance.util;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;

import org.karatachi.wicket.grid.Cells;
import org.karatachi.wicket.grid.DefaultCell;
import org.karatachi.wicket.grid.FixedGridPanel;
import org.karatachi.wicket.grid.FormattedCell;
import org.karatachi.wicket.grid.ICell;


public class CellFormatUtils {

    public static final Comparator<ICell> CELL_COMPARATOR =
            new Comparator<ICell>() {
                @Override
                public int compare(ICell o1, ICell o2) {
                    Object v1 = o1.getValue();
                    Object v2 = o2.getValue();
                    if (v1 instanceof Number && v2 instanceof Number) {
                        return Double.compare(((Number) v1).doubleValue(),
                                ((Number) v2).doubleValue());
                    } else if (v1 instanceof Number) {
                        return 1;
                    } else if (v2 instanceof Number) {
                        return -1;
                    } else {
                        String s1 = v1 != null ? v1.toString() : "";
                        String s2 = v2 != null ? v2.toString() : "";
                        return s1.compareTo(s2);
                    }
                }
            };

    public static ICell columnHeaderCell(Cells cells, int row, int col,
            Object value) {
        DefaultCell cell = new DefaultCell(value);
        cell.setClassName("ch");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell rowHeaderCell(Cells cells, int row, int col,
            Object value) {
        DefaultCell cell = new DefaultCell(value);
        cell.setClassName("rh");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell centerCell(Cells cells, int row, int col, String value) {
        DefaultCell cell = new DefaultCell(value);
        cell.setStyle("text-align: center;");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell centerCell(Cells cells, int row, int col, ICell cell) {
        cell.setStyle("text-align: center;");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell rightCell(Cells cells, int row, int col, Object value) {
        DefaultCell cell = new DefaultCell(value);
        cell.setStyle("text-align: right;");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell rightFormattedCell(Cells cells, int row, int col,
            Object value, String format) {
        FormattedCell cell = new FormattedCell(value, format);
        cell.setStyle("text-align: right;");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell leftCell(Cells cells, int row, int col, Object value) {
        DefaultCell cell = new DefaultCell(value);
        cell.setStyle("text-align: left;");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell leftFormattedCell(Cells cells, int row, int col,
            Object value, String format) {
        FormattedCell cell = new FormattedCell(value, format);
        cell.setStyle("text-align: left;");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell columnHeaderFormattedCell(Cells cells, int row,
            int col, Object value, String format) {
        FormattedCell cell = new FormattedCell(value, format);
        cell.setClassName("ch");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static ICell rowHeaderFormattedCell(Cells cells, int row, int col,
            Object value, String format) {
        FormattedCell cell = new FormattedCell(value, format);
        cell.setClassName("rh");
        cells.putCell(row, col, cell);
        return cell;
    }

    public static Cells drawItalic(Cells cells, int row) {
        final String style = "font-style: italic;";
        int cols = cells.getCols();
        for (int c = 1; c <= cols; ++c) {
            ICell cell = cells.getCell(row, c);
            if (cell != null) {
                if (cell.getStyle() != null) {
                    cell.setStyle(cell.getStyle() + ";" + style);
                } else {
                    cell.setStyle(style);
                }
            } else {
                cells.setValue(row, c, "").setStyle(style);
            }
        }
        return cells;
    }

    public static Cells drawVerticalItalic(Cells cells, int col) {
        final String style = "font-style: italic;";
        int rows = cells.getRows();
        for (int r = 1; r <= rows; ++r) {
            ICell cell = cells.getCell(r, col);
            if (cell != null) {
                if (cell.getStyle() != null) {
                    cell.setStyle(cell.getStyle() + ";" + style);
                } else {
                    cell.setStyle(style);
                }
            } else {
                cells.setValue(r, col, "").setStyle(style);
            }
        }
        return cells;
    }

    public static ICell drawItalic(ICell cell) {
        final String style = "font-style: italic;";
        if (cell != null) {
            if (cell.getStyle() != null) {
                cell.setStyle(cell.getStyle() + ";" + style);
            } else {
                cell.setStyle(style);
            }
        }
        return cell;
    }

    public static Cells drawDoubleLine(Cells cells, int row) {
        final String style =
                "border-bottom-style: double; border-bottom-width: 3px;";
        int cols = cells.getCols();
        for (int c = 1; c <= cols; ++c) {
            ICell cell = cells.getCell(row, c);
            if (cell != null) {
                if (cell.getStyle() != null) {
                    cell.setStyle(cell.getStyle() + ";" + style);
                } else {
                    cell.setStyle(style);
                }
            } else {
                cells.setValue(row, c, "").setStyle(style);
            }
        }
        return cells;
    }

    public static Cells drawDoubleLine(Cells cells, int row, int startCol,
            int endCol) {
        final String style =
                "border-bottom-style: double; border-bottom-width: 3px;";
        for (int c = startCol; c <= endCol; ++c) {
            ICell cell = cells.getCell(row, c);
            if (cell != null) {
                if (cell.getStyle() != null) {
                    cell.setStyle(cell.getStyle() + ";" + style);
                } else {
                    cell.setStyle(style);
                }
            } else {
                cells.setValue(row, c, "").setStyle(style);
            }
        }
        return cells;
    }

    public static Cells drawVerticalDoubleLine(Cells cells, int col) {
        final String style =
                "border-right-style: double; border-right-width: 3px;";
        int rows = cells.getRows();
        for (int r = 1; r <= rows; ++r) {
            ICell cell = cells.getCell(r, col);
            if (cell != null) {
                if (cell.getStyle() != null) {
                    cell.setStyle(cell.getStyle() + ";" + style);
                } else {
                    cell.setStyle(style);
                }
            } else {
                cells.setValue(r, col, "").setStyle(style);
            }
        }
        return cells;
    }

    public static Cells drawVerticalDoubleLine(Cells cells, int row, int col) {
        final String style =
                "border-right-style: double; border-right-width: 3px;";
        ICell cell = cells.getCell(row, col);
        if (cell != null) {
            if (cell.getStyle() != null) {
                cell.setStyle(cell.getStyle() + ";" + style);
            } else {
                cell.setStyle(style);
            }
        } else {
            cells.setValue(row, col, "").setStyle(style);
        }
        return cells;
    }
    
    public static Cells drawHighlight(Cells cells, int row) {
        final String clazz = "hl";
        int cols = cells.getCols();
        for (int c = 1; c <= cols; ++c) {
            ICell cell = cells.getCell(row, c);
            if (cell != null) {
                cell.setClassName(cell.getClassName() + " " + clazz);
            }
        }
        return cells;
    }

    /**
     * グリッドの幅を内容に合わせて調整を行う
     * 
     * @param grid
     *            FixedGridPanel
     */
    public static void adjustGridColumnWidth(FixedGridPanel grid) {
        if (grid.getModel() == null) {
            return;
        }
        Cells cells = grid.getModel().getObject();
        if (cells == null) {
            return;
        }
        if (cells.getCols() == 1 && cells.getRows() == 1) {
            grid.setWidth(1, 320);
        } else {
            int cols = cells.getCols();
            for (int c = 1; c <= cols; ++c) {
                grid.setWidth(c, getMaxColumnWidth(cells, c));
            }
        }
    }

    public static int getMaxColumnWidth(Cells cells, int col) {
        int maxLength = 0;
        int row = cells.getRows();
        if (row < 1 || cells.getCols() < 1) {
            return maxLength;
        }
        for (int i = 1; i <= row; i++) {
            ICell cell = cells.getCell(i, col);
            if (cell == null) {
                continue;
            }
            String str = cell.toString();
            Object value = str.replaceAll("<[^>]*>", "");
            if (value != null) {
                byte[] bytes = null;
                try {
                    if (value == null || value.toString() == null) {
                        continue;
                    }
                    bytes = value.toString().getBytes("SJIS");
                } catch (UnsupportedEncodingException use) {

                }
                if (bytes != null) {
                    int columnLength = bytes.length;
                    if (str.contains("<img")) {
                        // 国旗アイコン
                        columnLength += 2;
                    }
                    if (columnLength > maxLength) {
                        maxLength = columnLength;
                    }
                }
            }
        }
        return maxLength * 7 + 10;
    }
}
