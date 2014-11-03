package com.framgia.attendance.web.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;
import org.karatachi.wicket.grid.Cells;
import org.karatachi.wicket.grid.FormattedCell;
import org.karatachi.wicket.grid.ICell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Picture;
import com.aspose.cells.PlacementType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.VerticalAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.words.Underline;
import com.framgia.attendance.util.web.WebStringUtil;
import com.framgia.attendance.web.excel.ExcelBook.ExcelSheet;
import com.framgia.attendance.web.excel.ExcelBook.ExcelSheet.FreezePane;
import com.framgia.attendance.web.excel.ExcelBook.ExcelSheet.MergeData;

public class AsposeExcelBookResourceStream extends AbstractResourceStream {
    private static final long serialVersionUID = 1L;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Double NEGATIVE_ZERO = Double.valueOf(-0.0);
    private static final Double POSITIVE_ZERO = Double.valueOf(+0.0);

    private Locale locale;
    private ExcelBook excelBook;

    public AsposeExcelBookResourceStream(ExcelBook excelBook) {
        this.excelBook = excelBook;
    }

    @Override
    public String getContentType() {
        return "application/vnd.ms-excel";
    }

    @Override
    public Bytes length() {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InputStream getInputStream() throws ResourceStreamNotFoundException {
        try {
            Workbook workbook = new Workbook();
            if (excelBook.defaultXL != null) {
                workbook.read(excelBook.defaultXL);
                excelBook.defaultXL.close();
            } else {
                InputStream templateXL =
                        getClass().getResourceAsStream("default_template.xls");
                workbook.read(templateXL);
                templateXL.close();
                excelBook.isDeleteFirstSheet = true;
            }

            if (excelBook.isDeleteFirstSheet) {
                workbook.getWorksheets().removeSheet(0);
            }

            int rowBias = 0;
            if (excelBook.isPrintLogo) {
                rowBias += 2;
            }

            List<ExcelSheet> sheets = excelBook.getSheets();
            for (ExcelSheet sheet : sheets) {
                Worksheet wsheet =
                        workbook.getWorksheets().getSheet(sheet.sheetName);

                if (wsheet == null) {
                    wsheet = workbook.getWorksheets().addSheet(sheet.sheetName);
                }
                if (sheet.freezePane != null) {
                    FreezePane fp = sheet.freezePane;
                    wsheet.freezePanes(fp.freezeRow + rowBias, fp.freezeCol,
                            fp.visibleRow + rowBias, fp.visibleCol);
                }

                com.aspose.cells.Cells aCells = wsheet.getCells();
                Cells cells = sheet.cells;

                if (excelBook.isPrintLogo) {
                    InputStream is = excelBook.getLogoImage();
                    Picture pic =
                            wsheet.getShapes().addPicture(0, 0, is,
                                    excelBook.getLogoWidth(),
                                    excelBook.getLogoHeight());
                    is.close();
                    pic.setPlacement(PlacementType.FREE_FLOATING);
                    if (excelBook.logoHyperLinkURI != null) {
                        pic.addHyperlink(excelBook.logoHyperLinkURI);
                    } else {
                        pic.addHyperlink("https://www.ub-speeda.com/");
                    }
                }

                setupSheet(wsheet, sheet, rowBias);

                int rows = cells.getRows();
                int cols = cells.getCols();

                for (int r = 0; r < rows; ++r) {
                    for (int c = 0; c < cols; ++c) {
                        ICell cell = cells.getCell(r + 1, c + 1);
                        if (cell == null || cell.getValue() == null) {
                            if (cell != null) {
                                Cell aCell = aCells.getCell(r + rowBias, c);
                                aCell.setStyle(getCellStyle(aCell.getStyle(),
                                        cell));
                            }
                            continue;
                        }

                        Object obj = cell.getValue();
                        Cell aCell = aCells.getCell(r + rowBias, c);
                        if (obj == null) {
                            ;
                        } else if (obj instanceof Number) {
                            if (cell instanceof FormattedCell) {
                                setNumberCellFormatStyle(aCell,
                                        ((FormattedCell) cell).getFormat());
                            }
                            if (obj.equals(NEGATIVE_ZERO)) {
                                obj = POSITIVE_ZERO;
                            }
                            aCell.setValue(obj);
                        } else if (obj instanceof Date) {
                            aCell.setValue(obj);
                            Style style = aCell.getStyle();
                            style.setNumber(14);
                            aCell.setStyle(style);
                        } else {
                            aCell.setValue(WebStringUtil.eraceHtmlTags(obj.toString()));
                        }
                        aCell.setStyle(getCellStyle(aCell.getStyle(), cell));
                    }
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.save(baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ResourceStreamNotFoundException(e);
        }
    }

    private static final Pattern NUMBER_FORMAT_PATTERN =
            Pattern.compile("%(,?)\\.([1-9])f.*");

    private static void setNumberCellFormatStyle(Cell aCell, String format) {
        Style style = aCell.getStyle();
        Matcher m = NUMBER_FORMAT_PATTERN.matcher(format);
        if (m.matches()) {
            boolean hasComma = m.group(1).equals(",");
            int precision = Integer.valueOf(m.group(2));
            StringBuilder sb = new StringBuilder();
            sb.append("#" + (hasComma ? "," : "") + "##0.");
            for (int i = 1; i <= precision; i++) {
                sb.append("0");
            }
            style.setCustom(sb.toString());
        } else if (format.equals("%d")) {
            style.setCustom("###0");
        } else {
            style.setCustom("#,##0");
        }
        aCell.setStyle(style);
    }

    private Style getCellStyle(Style style, ICell cell) {
        String styleStr = cell.getStyle();
        if (StringUtils.isNotEmpty(styleStr)) {
            Map<String, String> styles = parseStyle(styleStr);
            if (styles.isEmpty() == false) {
                String value;
                if ((value = styles.get("text-align")) != null) {
                    style.setHAlignment(getAlignment(value));
                }
                if ((value = styles.get("vertical-align")) != null) {
                    style.setVAlignment(getVerticalAlignment(value));
                }
                if ((value = styles.get("background-color")) != null) {
                    style.setColor(getBackgroundColor(value));
                }
                if ((value = styles.get("border")) != null) {
                    setBorder(style, BorderType.TOP, value);
                    setBorder(style, BorderType.BOTTOM, value);
                    setBorder(style, BorderType.LEFT, value);
                    setBorder(style, BorderType.RIGHT, value);
                }
                if ((value = styles.get("border-top")) != null) {
                    setBorder(style, BorderType.TOP, value);
                }
                if ((value = styles.get("border-left")) != null) {
                    setBorder(style, BorderType.LEFT, value);
                }
                if ((value = styles.get("border-right")) != null) {
                    setBorder(style, BorderType.RIGHT, value);
                }
                if ((value = styles.get("border-bottom")) != null) {
                    setBorder(style, BorderType.BOTTOM, value);
                }
                if ((value = styles.get("border-bottom-style")) != null) {
                    setBorder(style, BorderType.BOTTOM, value);
                }
                style.setFont(setFontStyle(style.getFont(), styles));

                if ((value = styles.get("white-space")) != null) {
                    style.setTextWrapped(getWhiteSpace(value));
                }
            }
        }
        if (cell.getIndent() != 0) {
            style.setIndent(cell.getIndent() / 20);
        }
        return style;
    }

    private boolean getWhiteSpace(String whitespace) {
        if ("normal".equals(whitespace)) {
            return true;
        }
        return false;
    }

    private Font setFontStyle(Font font, Map<String, String> style) {
        String value;
        if ((value = style.get("font-size")) != null) {
            if (value.indexOf("%") >= 0) {
                // サポートせず
            } else {
                value = value.replace("px", "");
                font.setSize(Integer.valueOf(value));
            }
        }
        if ((value = style.get("font-family")) != null) {
            font.setName(value);
        }
        if ("bold".equals(style.get("font-weight"))) {
            font.setBold(true);
        }
        if ("italic".equals(style.get("font-style"))) {
            font.setItalic(true);
        }
        if ("underline".equals(style.get("text-decoration"))) {
            font.setUnderline(Underline.SINGLE);
        }
        if ((value = style.get("color")) != null) {
            Color c = getColor(value);
            if (c != null) {
                font.setColor(c);
            }
        }
        return font;
    }

    private void setBorder(Style style, int borderType, String styleStr) {
        int borderLineType = getBorderLineType(styleStr);
        style.setBorderLine(borderType, borderLineType);
        Color color = getBorderColor(styleStr);
        if (color != null) {
            style.setBorderColor(borderType, color);
        }
    }

    private int getBorderLineType(String style) {
        if (style == null) {
            return BorderLineType.NONE;
        }
        String[] styles = style.split(" ");
        for (String stl : styles) {
            if ("solid".equals(stl)) {
                return BorderLineType.THIN;
            } else if ("none".equals(stl)) {
                return BorderLineType.NONE;
            } else if ("double".equals(stl)) {
                return BorderLineType.DOUBLE;
            } else if ("dashed".equals(stl)) {
                return BorderLineType.DASHED;
            } else if ("dotted".equals(stl)) {
                return BorderLineType.DOTTED;
            } else if ("medium".equals(stl)) {
                return BorderLineType.MEDIUM;
            }
        }
        return BorderLineType.NONE;
    }

    private Color getBorderColor(String styleStr) {
        if (styleStr == null) {
            return null;
        }
        String[] styles = styleStr.split(" ");
        Color color = null;
        for (String stl : styles) {
            color = getColor(stl);
            if (color != null) {
                return color;
            }
        }
        return null;
    }

    private Color getBackgroundColor(String color) {
        Color c = getColor(color);
        if (c == null) {
            c = Color.WHITE;
        }
        return c;
    }

    private static final Pattern COLOR_PATTERN =
            Pattern.compile("#[0-9a-fA-F]{6}");
    private static final Map<String, Color> COLOR_MAP;
    static {
        Map<String, Color> map = new HashMap<>();
        map.put("white", Color.WHITE);
        map.put("black", Color.BLACK);
        map.put("blue", Color.BLUE);
        map.put("yellow", Color.YELLOW);
        map.put("lightgray", Color.SILVER);
        map.put("gray", Color.GRAY);
        map.put("lightcoral", new Color(240, 128, 128));
        COLOR_MAP = Collections.unmodifiableMap(map);
    }

    private Color getColor(String color) {
        if (color != null) {
            if (COLOR_PATTERN.matcher(color).matches()) {
                return new Color(Integer.parseInt(color.substring(1, 3), 16),
                        Integer.parseInt(color.substring(3, 5), 16),
                        Integer.parseInt(color.substring(5, 7), 16));
            } else {
                return COLOR_MAP.get(color);
            }
        }
        return null;
    }

    private short getVerticalAlignment(String valign) {
        if ("top".equals(valign)) {
            return VerticalAlignmentType.TOP;
        } else if ("middle".equals(valign)) {
            return VerticalAlignmentType.CENTRED;
        } else if ("bottom".equals(valign)) {
            return VerticalAlignmentType.BOTTOM;
        }
        return VerticalAlignmentType.JUSTIFIED;
    }

    private short getAlignment(String align) {
        if ("left".equals(align)) {
            return TextAlignmentType.LEFT;
        } else if ("right".equals(align)) {
            return TextAlignmentType.RIGHT;
        } else if ("center".equals(align)) {
            return TextAlignmentType.CENTER;
        } else if ("justify".equals(align)) {
            return TextAlignmentType.JUSTIFY;
        }
        return TextAlignmentType.JUSTIFY;
    }

    private static final Pattern CSS_PATTERN =
            Pattern.compile("([^:;]*):([^;]*)");

    /**
     * CSSスタイルをMapに格納します。
     * 
     * @param style
     *            cssスタイルの文字列
     * @return
     */
    protected Map<String, String> parseStyle(String style) {
        Map<String, String> result = new HashMap<String, String>();
        if (style == null) {
            return result;
        }
        Matcher m = CSS_PATTERN.matcher(style);
        while (m.find()) {
            String key = m.group(1);
            String value = m.group(2);
            result.put(key.trim(), value.trim());
        }
        return result;
    }

    private void setupSheet(Worksheet wsheet, ExcelSheet sheet, int rowBias) {
        com.aspose.cells.Cells cells = wsheet.getCells();
        if (sheet.width != null) {
            Integer size;
            for (int i = 0; i < 256; i++) {
                size = sheet.width.get(i);
                if (size != null) {
                    cells.setColumnWidth(i, size);
                } else {
                    cells.setColumnWidth(i, 12);
                }
            }
        }
        if (sheet.height != null) {
            Set<Integer> keySet = sheet.height.keySet();
            Integer size;
            for (Integer key : keySet) {
                size = sheet.height.get(key);
                if (size != null) {
                    cells.setRowHeight(key + rowBias, size);
                }
            }
        }
        if (sheet.merges != null && sheet.merges.size() > 0) {
            for (MergeData merge : sheet.merges) {
                cells.merge(merge.startRow + rowBias, merge.startCol,
                        merge.endRow + rowBias, merge.endCol);
            }
        }
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Time lastModifiedTime() {
        return Time.now();
    }
}
