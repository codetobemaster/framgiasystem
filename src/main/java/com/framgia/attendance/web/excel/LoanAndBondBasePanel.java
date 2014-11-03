package com.framgia.attendance.web.excel;

import org.apache.wicket.markup.html.panel.Panel;

public abstract class LoanAndBondBasePanel extends Panel {

    public LoanAndBondBasePanel(String id) {
        super(id);
    }
    // private static final long serialVersionUID = 1L;
    //
    // @Binding
    // protected LoanAndBondLogic loanAndBondLogic;
    //
    // protected final FixedGridPanel plan;
    // protected final FixedGridPanel kind;
    //
    // protected final CorpBss corpBss;
    // protected final LoanAndBondParameter parameter;
    // protected final LoanAndBondPlanParameter planParameter;
    //
    // protected IModel<Cells> kindModel;
    //
    // public enum LoanAndBondType {
    // Loan("借入金", "種類別借入金明細", "Loan", "返済予定額"), //
    // Bond("社債", "種類別社債明細", "Bond", "償還予定額"), //
    // LoanBond("借入金及び社債", "短期借入債務及び長期借入債務明細", "LoanAndBond", "返済額"), ;
    //
    // private String pageTitle;
    // private String kindExcelTitle;
    // private String fileSuffix;
    // private String planTitle;
    //
    // private LoanAndBondType(String pageTitle, String kindExcelTitle,
    // String fileSuffix, String planTitle) {
    // this.pageTitle = pageTitle;
    // this.kindExcelTitle = kindExcelTitle;
    // this.fileSuffix = fileSuffix;
    // this.planTitle = planTitle;
    // }
    //
    // public String getPageTitle() {
    // return pageTitle;
    // }
    //
    // public String getKindExcelTitle() {
    // return kindExcelTitle;
    // }
    //
    // public String getFileSuffix() {
    // return fileSuffix;
    // }
    //
    // public String getPlanTitle() {
    // return planTitle;
    // }
    //
    // public boolean isLoan() {
    // return Loan == this;
    // }
    //
    // public boolean isBond() {
    // return Bond == this;
    // }
    //
    // }
    //
    // public LoanAndBondBasePanel(String id, CorpBss corpBss, LoanAndBondType
    // type) {
    // super(id);
    //
    // this.corpBss = corpBss;
    // this.parameter = new LoanAndBondParameter();
    // this.planParameter = new LoanAndBondPlanParameter();
    //
    // parameter.scale = CurrencyScale.MILLION;
    // parameter.balance = true;
    // parameter.rate = true;
    // parameter.issued = true;
    // parameter.repayment = true;
    // parameter.redemption = true;
    // planParameter.scale = CurrencyScale.MILLION;
    //
    // final Period range = getValidPeriod();
    //
    // if (range != null) {
    // parameter.fromYear =
    // Math.max(range.getToYear() - 5, range.getFromYear());
    // parameter.toYear = range.getToYear();
    // }
    //
    // String fileNamePlan =
    // AttachedFileNameUtil.createDownloadFileName(
    // "Company_" + type.getFileSuffix() + "_Plan",
    // FileType.excel, corpBss.globalId);
    // String fileNameKind =
    // AttachedFileNameUtil.createDownloadFileName(
    // "Company_" + type.getFileSuffix() + "_Kind",
    // FileType.excel, corpBss.globalId);
    //
    // /*
    // * 返済予定
    // */
    // final LoanAndBondPlanDataDto dto = getPlanData();
    //
    // final IModel<Cells> planModel = new LoadableDetachableModel<Cells>() {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // protected Cells load() {
    // return getPlan(dto);
    // }
    // };
    //
    // add(new LoanAndBondPlanParameterPanel("planParameter",
    // new PropertyModel<LoanAndBondPlanParameter>(this,
    // "planParameter"), type) {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // protected void onUpdate(AjaxRequestTarget target) {
    // target.add(plan);
    // }
    //
    // @Override
    // public boolean isVisible() {
    // return getPlan(dto) != null;
    // }
    // });
    //
    // String periodStr;
    // if (dto != null && dto.fnacRep != null) {
    // int accSttPeriodNum = dto.fnacRep.accSttlPeriodNum;
    // periodStr =
    // "("
    // + String.format("%04d年%02d月末時点",
    // accSttPeriodNum / 100,
    // accSttPeriodNum % 100) + ")";
    // } else {
    // // periodStr = "(----年--月末時点)";
    // periodStr = "";
    // }
    //
    // add(new ExcelBookDownloadLink("downloadPlan", fileNamePlan,
    // new ExcelDownloadModel(planModel, true, type.getPageTitle()
    // + type.getPlanTitle() + periodStr, type.getPlanTitle(),
    // planParameter)));
    //
    // add(new Label("issuedate", periodStr));
    // add(plan = new HasDataFixedGridPanel("plan", planModel));
    // add(new NoDataContainer("noplan", planModel));
    //
    // plan.setDefalutWidth(85);
    // plan.setDefalutHeight(18);
    // plan.setWidth(1, 200);
    // plan.fix(3, 0);
    //
    // /*
    // * 返済種別
    // */
    // IModel<Cells> kindModel = new LoadableDetachableModel<Cells>() {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // protected Cells load() {
    // Cells cells = getKind(false);
    // if (cells != null) {// 返済期限や償還期限の幅を広げる
    // boolean continuation = false;
    // for (int col = 3; col <= cells.getCols(); col++) {
    // kind.setWidth(col, 85);// 初期化
    // if (parameter.itemUp) {
    // ICell cell = cells.getCell(1, col);
    // if ((cell != null
    // && isTermOfPaymentTitle(cell.getValue()) || //
    // ((cell == null || cell.getValue() == null) && continuation))) {
    // kind.setWidth(col, 170);
    // continuation = true;// 次の項目が現れるまでWidth設定制御を続けるため
    // } else {
    // continuation = false;
    // }
    //
    // } else {
    // ICell cell = cells.getCell(3, col);
    // if (cell != null
    // && isTermOfPaymentTitle(cell.getValue())) {
    // kind.setWidth(col, 150);
    // }
    // }
    // }
    // }
    // return cells;
    // }
    //
    // private boolean isTermOfPaymentTitle(Object value) {
    // if (value == null) {
    // return false;
    // }
    // if ("返済期限".equals(value) || "償還期限".equals(value)) {
    // return true;
    // }
    // return false;
    // }
    // };
    // IModel<Cells> kindExcelModel = new LoadableDetachableModel<Cells>() {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // protected Cells load() {
    // Cells cells = getKind(true);
    // if (cells != null) {
    // boolean isRate = false;
    // for (int col = 3; col <= cells.getCols(); col++) {
    // if (parameter.itemUp) {
    // ICell cell = cells.getCell(1, col);
    // if (cell != null
    // && cell.getValue() instanceof String
    // && ((String) cell.getValue()).startsWith("利率")) {
    // setDefaultFormat(cells, col);
    // isRate = true;
    // } else if (isRate
    // && (cell == null || cell.getValue() == null)) {
    // setDefaultFormat(cells, col);
    // } else {
    // isRate = false;
    // }
    // } else {
    // ICell cell = cells.getCell(3, col);
    // if (cell != null
    // && cell.getValue() instanceof String
    // && ((String) cell.getValue()).startsWith("利率")) {
    // setDefaultFormat(cells, col);
    // }
    // }
    // }
    // }
    //
    // return cells;
    // }
    // };
    //
    // add(new LoanAndBondParameterPanel("parameter",
    // new PropertyModel<LoanAndBondParameter>(this, "parameter"),
    // type) {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // protected void onUpdate(AjaxRequestTarget target) {
    // target.add(LoanAndBondBasePanel.this);
    // }
    //
    // @Override
    // public boolean isVisible() {
    // return range != null;
    // }
    // }.setRange(range));
    //
    // if (type != LoanAndBondType.Bond) {
    // add(new AjaxLink<Void>("shuffle") {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void onClick(AjaxRequestTarget target) {
    // parameter.itemUp = !parameter.itemUp;
    // target.add(LoanAndBondBasePanel.this);
    // }
    // });
    // }
    // add(new ExcelBookDownloadLink("downloadKind", fileNameKind,
    // new ExcelDownloadModel(kindExcelModel, false,
    // type.getKindExcelTitle(), type.getKindExcelTitle())));
    //
    // add(kind = new HasDataFixedGridPanel("kind", kindModel));
    // add(new NoDataContainer("nokind", kindModel));
    // add(new NoDataContainer2("nokind2", kindModel));
    //
    // kind.setDefalutWidth(85);
    // kind.setDefalutHeight(18);
    // kind.setWidth(1, 300);
    // kind.setWidth(2, 300);
    // kind.fix(3, 0);
    //
    // setOutputMarkupId(true);
    // }
    //
    // private static void setDefaultFormat(Cells cells, int col) {
    // // 利率カラムの各セルをFormattedCellからDefaultCellに変更する
    // // Excel出力時、固定小数点ではなく、標準書式とするため
    // for (int row = 3; row <= cells.getRows(); row++) {
    // ICell cell = cells.getCell(row, col);
    // if (cell instanceof FormattedCell) {
    // FormattedCell fcell = (FormattedCell) cell;
    // String f = fcell.getFormat();
    // DefaultCell dcell = new DefaultCell(fcell.getValue());
    // dcell.setStyle(fcell.getStyle());
    // cells.putCell(row, col, dcell);
    // } else {
    // String style = cell.getStyle();
    // cell.setStyle("text-align:right;"
    // + ((style != null) ? style : ""));
    // }
    // }
    // }
    //
    // private class ExcelDownloadModel extends
    // LoadableDetachableModel<ExcelBook> {
    // private static final long serialVersionUID = 1L;
    //
    // private final IModel<Cells> model;
    // private final boolean plan;
    // private final String title;
    // private final String sheetName;
    // private final LoanAndBondPlanParameter planParameter;
    //
    // public ExcelDownloadModel(IModel<Cells> model, boolean plan,
    // String title, String sheetName) {
    // this(model, plan, title, sheetName, null);
    // }
    //
    // public ExcelDownloadModel(IModel<Cells> model, boolean plan,
    // String title, String sheetName,
    // LoanAndBondPlanParameter planParameter) {
    // this.model = model;
    // this.plan = plan;
    // this.title = title;
    // this.sheetName = sheetName;
    // this.planParameter = planParameter;
    // }
    //
    // @Override
    // protected ExcelBook load() {
    // Cells cells = model.getObject();
    //
    // if (plan) {
    // cells.removeRow(1);
    // } else {
    // if (parameter.itemUp) {
    // if (cells.getRows() >= 2) {
    // cells.removeRow(2);
    // }
    // } else {
    // cells.setValue(2, 1, cells.getValue(1, 1));
    // cells.setValue(2, 2, cells.getValue(1, 2));
    // cells.removeRow(1);
    // }
    // }
    //
    // CellsUtil.setFontSizeAll(cells, 9);
    // CellsUtil.changeHeaderStyle(cells);
    // CellsUtil.setGridValueStyle(cells, 1, 1, cells.getRows(),
    // cells.getCols());
    // int row =
    // CellsUtil.appendCompIdstData(cells, PlaceType.COMPANY,
    // corpBss.globalId);
    // row = CellsUtil.appendPageTitle(cells, row, title);
    //
    // if (plan) {
    // cells.insertRow(row + 1);
    // ICell cell =
    // new DefaultCell("（単位：" + planParameter.scale.toString()
    // + "円）");
    // cell.setStyle("text-align:right;");
    // cells.putCell(row + 1, cells.getCols(), cell);
    // } else {
    // List<SupplimentInfoDto> suppliment =
    // new ArrayList<CellsUtil.SupplimentInfoDto>();
    // suppliment.add(new SupplimentInfoDto("単位",
    // parameter.scale.toString() + "円"));
    // CellsUtil.appendSupplimentInfo(cells, row, suppliment);
    // }
    //
    // ExcelBook book = new ExcelBook();
    //
    // ExcelSheet sheet = book.createSheet(sheetName, cells);
    //
    // sheet.mergeColumnSpans();
    //
    // if (plan) {
    // sheet.addMergeRow(1, 8, 9);
    // sheet.setFreezePane(10, 2);
    // sheet.setWidth(1, 20);
    // } else {
    // sheet.addMergeRow(1, 9, 10);
    // sheet.addMergeRow(2, 9, 10);
    // sheet.setFreezePane(11, 3);
    // sheet.setWidth(1, 20);
    // sheet.setWidth(2, 24);
    //
    // /*
    // * ※仕様著抜粋
    // * 注記情報がある場合のみ、高さ30,横33で固定
    // * 開示がない場合は、高さは元のまま（12）とする
    // */
    // int startNoteCol = 0;
    // boolean findNoteCol = false;
    // for (int col = 3; col <= cells.getCols(); col++) {
    // ICell cell1 = cells.getCell(9, col);
    // ICell cell2 = cells.getCell(10, col);
    // if (findNoteCol == false
    // && (hasNoteInfo(cell1) || hasNoteInfo(cell2))) {
    // findNoteCol = true;
    // startNoteCol = col;
    // }
    // if (findNoteCol) {
    // sheet.setWidth(col, 33);
    // }
    // }
    // if (findNoteCol) {
    // for (int r = 11; r <= cells.getRows(); r++) {
    // boolean findNoteRow = false;
    // for (int c = startNoteCol; c <= cells.getCols(); c++) {
    // ICell cell = cells.getCell(r, c);
    // if (cell != null && cell.getValue() != null) {
    // findNoteRow = true;
    // break;
    // }
    // }
    // if (findNoteRow) {
    // sheet.setHeight(r, 30);
    // }
    // }
    // }
    // }
    //
    // return book;
    // }
    //
    // private boolean hasNoteInfo(ICell cell) {
    // return cell != null
    // && cell.getValue() != null
    // && cell.getValue().toString().contains(
    // LoanAndBondLogic.NOTE_INFO);
    // }
    //
    // @Override
    // public void detach() {
    // super.detach();
    // model.detach();
    // }
    // }
    //
    // class HasDataFixedGridPanel extends FixedGridPanel {
    // private static final long serialVersionUID = 1L;
    //
    // public HasDataFixedGridPanel(String id, IModel<Cells> cells) {
    // super(id, cells);
    // setOutputMarkupId(true);
    // setOutputMarkupPlaceholderTag(true);
    // }
    //
    // @Override
    // public boolean isVisible() {
    // Object object = getDefaultModelObject();
    // return (object != null) && !isNoDataWithCondition(object);
    // }
    // }
    //
    // class NoDataContainer extends WebMarkupContainer {
    // private static final long serialVersionUID = 1L;
    //
    // public NoDataContainer(String id, IModel<?> model) {
    // super(id, model);
    // }
    //
    // @Override
    // public boolean isVisible() {
    // return getDefaultModelObject() == null;
    // }
    // }
    //
    // class NoDataContainer2 extends WebMarkupContainer {
    // private static final long serialVersionUID = 1L;
    //
    // public NoDataContainer2(String id, IModel<?> model) {
    // super(id, model);
    // setOutputMarkupId(true);
    // setOutputMarkupPlaceholderTag(true);
    // }
    //
    // @Override
    // public boolean isVisible() {
    // return isNoDataWithCondition(getDefaultModelObject());
    // }
    // }
    //
    // class KindNoticeContainer extends WebMarkupContainer {
    // private static final long serialVersionUID = 1L;
    //
    // public KindNoticeContainer(String id, IModel<?> model) {
    // super(id, model);
    // setOutputMarkupId(true);
    // setOutputMarkupPlaceholderTag(true);
    // }
    //
    // @Override
    // public boolean isVisible() {
    // Object object = getDefaultModelObject();
    // if (object == null) {
    // return false;
    // }
    // return !isNoDataWithCondition(object);
    // }
    // }
    //
    // protected abstract LoanAndBondPlanDataDto getPlanData();
    //
    // protected abstract Period getValidPeriod();
    //
    // protected abstract Cells getPlan(LoanAndBondPlanDataDto planData);
    //
    // protected abstract Cells getKind(boolean isExcel);
    //
    // protected boolean isNoDataWithCondition(Object object) {
    // if (object == null || !(object instanceof Cells)) {
    // return false;
    // } else {
    // return ((Cells) object).getRows() < 4;
    // }
    // }
}
