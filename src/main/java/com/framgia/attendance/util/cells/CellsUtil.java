package com.framgia.attendance.util.cells;

import java.util.List;

import org.karatachi.wicket.grid.Cells;
import org.karatachi.wicket.grid.DefaultCell;
import org.karatachi.wicket.grid.ICell;

public class CellsUtil {

    public static enum PlaceType {
        COMPANY, INDUSTRY, OTHEER;
    }

    public static int appendPageTitle(Cells cells, int row, String pageTitle) {
        cells.insertRow(row);
        row++;
        cells.insertRow(row);

        int col = cells.getCols();
        for (int c = 1; c <= col; c++) {
            String value;
            if (c == 1) {
                value = pageTitle;
            } else {
                value = null;
            }
            ICell cell = new DefaultCell(value);
            cell.setStyle("border-bottom:double #000000;font-weight:bold;font-size:11px;");
            cells.putCell(row, c, cell);
        }
        row++;
        cells.insertRow(row);

        return row;
    }

    public static class SupplimentInfoDto {
        String title;
        Object obj;

        public SupplimentInfoDto(String title, Object obj) {
            this.title = title;
            this.obj = obj;
        }
    }

    public static int appendSupplimentInfo(Cells cells, int row,
            List<SupplimentInfoDto> dtoList) {
        if (dtoList != null && dtoList.size() <= 0) {
            return row;
        }
        cells.insertRow(row);

        int col = cells.getCols();
        ICell cell;
        SupplimentInfoDto dto;
        for (int c = 1; c <= col; c++) {
            if (c <= dtoList.size()) {
                dto = dtoList.get(c - 1);
                cell = new DefaultCell(dto.title);
            } else {
                cell = new DefaultCell();
            }
            cell.setStyle("font-weight:bold;font-size:9px;");
            cells.putCell(row, c, cell);
        }
        row++;
        cells.insertRow(row);

        for (int c = 1; c <= col; c++) {
            if (c <= dtoList.size()) {
                dto = dtoList.get(c - 1);
                cell = new DefaultCell(dto.obj);
            } else {
                cell = new DefaultCell();
            }
            cell.setStyle("font-size:9px;");
            cells.putCell(row, c, cell);
        }
        row++;

        return row;
    }

    /**
     * 一定の範囲にヘッダクラスを埋め込む必要がある場合にクラス"ch"を埋め込む。
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     */
    public static void fillUpRowHeader(Cells cells, int startRow, int startCol,
            int endRow, int endCol) {
        int row = endRow;
        int col = endCol;

        for (int r = startRow; r <= row; r++) {
            for (int c = startCol; c <= col; c++) {
                ICell cell = cells.getCell(r, c);
                if (cell == null) {
                    cell = new DefaultCell();
                    cell.setClassName("rh");
                    cells.putCell(r, c, cell);
                } else {
                    if (cell.getClassName() == null) {
                        cell.setClassName("rh");
                    } else {
                        cell.setClassName(cell.getClassName() + " rh");
                    }
                }
            }
        }
    }

    /**
     * 一定の範囲にヘッダクラスを埋め込む必要がある場合にクラス"ch"を埋め込む。
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     */
    public static void fillUpColHeader(Cells cells, int startRow, int startCol,
            int endRow, int endCol) {
        int row = endRow;
        int col = endCol;

        for (int r = startRow; r <= row; r++) {
            for (int c = startCol; c <= col; c++) {
                ICell cell = cells.getCell(r, c);
                if (cell == null) {
                    cell = new DefaultCell();
                    cell.setClassName("ch");
                    cells.putCell(r, c, cell);
                } else {
                    if (cell.getClassName() == null) {
                        cell.setClassName("ch");
                    } else {
                        cell.setClassName(cell.getClassName() + " ch");
                    }
                }
            }
        }
    }

    /**
     * ヘッダクラスのあるセルに基本的なヘッダスタイルを設定する。
     * 
     * @param cells
     *            対象Cells
     */
    public static void changeHeaderStyle(Cells cells) {
        int row = cells.getRows();
        int col = cells.getCols();
        ICell cell;

        String bgcolor = "background-color:#CCCCCC;";
        String border = "border: solid #AAAAAA;";
        String borderNt =
                "border-left: solid #AAAAAA; border-right: solid #AAAAAA; border-bottom: solid #AAAAAA;";
        String borderNb =
                "border-top: solid #AAAAAA; border-left: solid #AAAAAA; border-right: solid #AAAAAA;";
        String borderLt =
                "border-top: solid #AAAAAA; border-left: solid #AAAAAA;";
        String borderNl =
                "border-top: solid #AAAAAA; border-right: solid #AAAAAA; border-right: solid #AAAAAA;";

        String className;
        String[] classNames;
        for (int r = 1; r <= row; r++) {
            for (int c = 1; c <= col; c++) {
                cell = cells.getCell(r, c);
                if (cell != null) {
                    className = cell.getClassName();
                    if (className != null) {
                        classNames = className.split(" ");
                    } else {
                        classNames = new String[0];
                    }
                    for (String clnm : classNames) {
                        if ("ch".equals(clnm) || "rh".equals(clnm)
                                || "h".equals(clnm)) {
                            cell.setStyle((cell.getStyle() != null
                                    ? cell.getStyle() + " " : "")
                                    + bgcolor
                                    + border);
                            break;
                        }
                        if ("ch-nt".equals(clnm)) {
                            cell.setStyle((cell.getStyle() != null
                                    ? cell.getStyle() + " " : "")
                                    + " "
                                    + bgcolor + borderNt);
                            break;
                        }
                        if ("ch-nb".equals(clnm)) {
                            cell.setStyle((cell.getStyle() != null
                                    ? cell.getStyle() + " " : "")
                                    + " "
                                    + bgcolor + borderNb);
                            break;
                        }
                        if ("ch-lt".equals(clnm)) {
                            cell.setStyle((cell.getStyle() != null
                                    ? cell.getStyle() + " " : "")
                                    + " "
                                    + bgcolor + borderLt);
                            break;
                        }
                        if ("ch-nl".equals(clnm)) {
                            cell.setStyle((cell.getStyle() != null
                                    ? cell.getStyle() + " " : "")
                                    + " "
                                    + bgcolor + borderNl);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 全範囲に対し、フォントサイズを設定する。
     * 
     * @param cells
     *            対象Cells
     * @param size
     *            サイズ
     */
    public static void setFontSizeAll(Cells cells, int size) {
        setFontSize(cells, 1, 1, cells.getRows(), cells.getCols(), size);
    }

    /**
     * 一定の範囲に対し、フォントサイズを設定する。
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     * @param size
     *            サイズ
     */
    public static void setFontSize(Cells cells, int startRow, int startCol,
            int endRow, int endCol, int size) {
        setStyle(cells, startRow, startCol, endRow, endCol, "font-size:" + size
                + "px;");
    }

    /**
     * 全範囲に対し、折り返し設定をする。
     * 
     * @param cells
     *            対象Cells
     */
    public static void onTurnStyleAll(Cells cells) {
        onTurnStyle(cells, 1, 1, cells.getRows(), cells.getCols());
    }

    /**
     * 一定の範囲に対し、折り返し設定をする。
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     */
    public static void onTurnStyle(Cells cells, int startRow, int startCol,
            int endRow, int endCol) {
        setStyle(cells, startRow, startCol, endRow, endCol,
                "white-space: normal;");
    }

    /**
     * 一定の範囲の境界線の色を#AAAAAAに設定するメソッド
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     */
    public static void setGridValueStyle(Cells cells, int startRow,
            int startCol, int endRow, int endCol) {
        setStyle(cells, startRow, startCol, endRow, endCol,
                "border: solid #AAAAAA;", true);
    }

    /**
     * 全範囲に対し、指定スタイルの設定をする。
     * 
     * @param cells
     *            対象Cells
     * @param style
     *            指定スタイル
     */
    public static void setStyleAll(Cells cells, String style) {
        setStyle(cells, 1, 1, cells.getRows(), cells.getCols(), style, false);
    }

    /**
     * 全範囲に対し、指定スタイルの設定をする。
     * 
     * @param cells
     *            対象Cells
     * @param style
     *            指定スタイル
     * @param fillUp
     *            空Cellがあった場合に同一スタイルで埋める場合はtrue、そうで無い場合はfalse
     */
    public static void setStyleAll(Cells cells, String style, boolean fillUp) {
        setStyle(cells, 1, 1, cells.getRows(), cells.getCols(), style, fillUp);
    }

    /**
     * 一定の範囲に対し、指定スタイルの設定をする。
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     * @param style
     *            指定スタイル
     */
    public static void setStyle(Cells cells, int startRow, int startCol,
            int endRow, int endCol, String style) {
        setStyle(cells, startRow, startCol, endRow, endCol, style, false);
    }

    /**
     * 一定の範囲に対し、指定スタイルの設定をする。
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     * @param style
     *            指定スタイル
     * @param fillUp
     *            空Cellがあった場合に同一スタイルで埋める場合はture、そうで無い場合はfalse
     */
    public static void setStyle(Cells cells, int startRow, int startCol,
            int endRow, int endCol, String style, boolean fillUp) {
        ICell cell;
        for (int r = startRow; r <= endRow; r++) {
            for (int c = startCol; c <= endCol; c++) {
                cell = cells.getCell(r, c);
                if (cell != null) {
                    cell.setStyle((cell.getStyle() != null ? cell.getStyle()
                            + " " : "")
                            + style);
                } else {
                    if (fillUp) {
                        cell = new DefaultCell("");
                        cell.setStyle(style);
                        cells.putCell(r, c, cell);
                    }
                }
            }
        }
    }

    /**
     * セルの一列にスタイルを適用するメソッド
     * 
     * @param cells
     *            対象Cells
     * @param row
     *            行
     * @param style
     *            指定スタイル
     */
    public static void setStyleInRow(final Cells cells, final int row,
            final String style) {
        setStyle(cells, row, 1, row, cells.getCols(), style);
    }

    /**
     * セルの一列にスタイルを適用するメソッド
     * 
     * @param cells
     *            対象Cells
     * @param col
     *            列
     * @param style
     *            指定スタイル
     */
    public static void setStyleInCol(final Cells cells, final int col,
            final String style) {
        setStyle(cells, 1, col, cells.getRows(), col, style);
    }

    /**
     * セルを左寄せするメソッド
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     */
    public static void alignLeft(final Cells cells, final int startRow,
            final int startCol, final int endRow, final int endCol) {
        setStyle(cells, startRow, startCol, endRow, endCol, "text-align: left;");
    }

    /**
     * セルを右寄せするメソッド
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     */
    public static void alignRight(final Cells cells, final int startRow,
            final int startCol, final int endRow, final int endCol) {
        setStyle(cells, startRow, startCol, endRow, endCol,
                "text-align: right;");
    }

    /**
     * セルを中央寄せするメソッド
     * 
     * @param cells
     *            対象Cells
     * @param startRow
     *            範囲開始行
     * @param startCol
     *            範囲開始列
     * @param endRow
     *            範囲終了行
     * @param endCol
     *            範囲終了列
     */
    public static void alignCenter(final Cells cells, final int startRow,
            final int startCol, final int endRow, final int endCol) {
        setStyle(cells, startRow, startCol, endRow, endCol,
                "text-align: center;");

    }
}
