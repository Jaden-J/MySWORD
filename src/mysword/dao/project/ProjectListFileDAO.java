package mysword.dao.project;

import mysword.bean.project.ProjectBean;
import mysword.bean.project.ProjectStructureBean;
import mysword.system.conf.SystemConf;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProjectListFileDAO {

    private int rowNumber = 0;
    public XSSFWorkbook workbook;
    private ArrayList<ProjectBean> projectList;
    public XSSFCellStyle headerStyle;
    public XSSFCellStyle regionStyle;
    public XSSFCellStyle subTypeStyle;
    public XSSFCellStyle numericSubTypeStyle;
    public XSSFCellStyle itemCellStyle;
    public XSSFCellStyle numericItemCellStyle;

    public ByteArrayInputStream generateExcelByWeek(String weekStr) throws IOException, SQLException, ClassNotFoundException {
        projectList = ProjectDAO.getProjects(weekStr, null, null, null, null, null, null, null, null, -2, -2);
        workbook = new XSSFWorkbook();
        initHeaderStyle(workbook);
        initRegionStyle(workbook);
        initSubTypeStyle(workbook);
        initNumericSubTypeStyle(workbook);
        initItemCellStyle(workbook);
        initNumericItemCellStyle(workbook);
        XSSFSheet sheet = workbook.createSheet(weekStr);
        generateHeader(sheet);
        generateRegions(sheet);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public void syncProjectList(String weekStr) throws SQLException, ClassNotFoundException, IOException {
        projectList = ProjectDAO.getProjects(weekStr, null, null, null, null, null, null, null, null, -2, -2);
        String sheetName = "CW "+weekStr.substring(5)+"_"+weekStr.substring(2,4);
        workbook = new XSSFWorkbook(new FileInputStream(getPorjectListFilePath()));
        initHeaderStyle(workbook);
        initRegionStyle(workbook);
        initSubTypeStyle(workbook);
        initNumericSubTypeStyle(workbook);
        initItemCellStyle(workbook);
        initNumericItemCellStyle(workbook);
        if(workbook.getSheetIndex(sheetName) != -1) {
            workbook.removeSheetAt(workbook.getSheetIndex(sheetName));
        }
        XSSFSheet sheet = workbook.createSheet(sheetName);
        workbook.setSheetOrder(sheetName, 0);
        workbook.setActiveSheet(0);
        generateHeader(sheet);
        generateRegions(sheet);
        workbook.write(new FileOutputStream(new File(getPorjectListFilePath())));
    }

    private void initHeaderStyle(XSSFWorkbook workbook) {
        headerStyle = workbook.createCellStyle();
        headerStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerStyle.setLeftBorderColor(new XSSFColor(Color.BLACK));
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setRightBorderColor(new XSSFColor(Color.BLACK));
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)230, (byte)230, (byte)230}));
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }

    private void initRegionStyle(XSSFWorkbook workbook) {
        regionStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        regionStyle.setFont(font);
        regionStyle.setAlignment(HorizontalAlignment.LEFT);
        regionStyle.setBorderTop(CellStyle.BORDER_THICK);
        regionStyle.setTopBorderColor(new XSSFColor(new byte[]{(byte)0, (byte)0, (byte)0}));
        regionStyle.setBorderLeft(CellStyle.BORDER_THIN);
        regionStyle.setLeftBorderColor(new XSSFColor(Color.BLACK));
        regionStyle.setBorderRight(CellStyle.BORDER_THIN);
        regionStyle.setRightBorderColor(new XSSFColor(Color.BLACK));
    }

    private void initSubTypeStyle(XSSFWorkbook workbook) {
        subTypeStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        subTypeStyle.setFont(font);
        subTypeStyle.setBorderLeft(CellStyle.BORDER_THIN);
        subTypeStyle.setLeftBorderColor(new XSSFColor(Color.BLACK));
        subTypeStyle.setBorderRight(CellStyle.BORDER_THIN);
        subTypeStyle.setRightBorderColor(new XSSFColor(Color.BLACK));
        subTypeStyle.setBorderTop(CellStyle.BORDER_THIN);
        subTypeStyle.setTopBorderColor(new XSSFColor(Color.BLACK));
        subTypeStyle.setBorderBottom(CellStyle.BORDER_THIN);
        subTypeStyle.setBottomBorderColor(new XSSFColor(Color.BLACK));
        subTypeStyle.setFont(font);
        subTypeStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)205, (byte)205, (byte)205}));
        subTypeStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }

    private void initNumericSubTypeStyle(XSSFWorkbook workbook) {
        numericSubTypeStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        numericSubTypeStyle.setFont(font);
        numericSubTypeStyle.setBorderLeft(CellStyle.BORDER_THIN);
        numericSubTypeStyle.setLeftBorderColor(new XSSFColor(Color.BLACK));
        numericSubTypeStyle.setBorderRight(CellStyle.BORDER_THIN);
        numericSubTypeStyle.setRightBorderColor(new XSSFColor(Color.BLACK));
        numericSubTypeStyle.setBorderTop(CellStyle.BORDER_THIN);
        numericSubTypeStyle.setTopBorderColor(new XSSFColor(Color.BLACK));
        numericSubTypeStyle.setBorderBottom(CellStyle.BORDER_THIN);
        numericSubTypeStyle.setBottomBorderColor(new XSSFColor(Color.BLACK));
        numericSubTypeStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        numericSubTypeStyle.setFont(font);
        numericSubTypeStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)205, (byte)205, (byte)205}));
        numericSubTypeStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        numericSubTypeStyle.setAlignment(HorizontalAlignment.CENTER);
    }

    private void initItemCellStyle(XSSFWorkbook workbook) {
        itemCellStyle = workbook.createCellStyle();
        itemCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        itemCellStyle.setLeftBorderColor(new XSSFColor(Color.BLACK));
        itemCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        itemCellStyle.setRightBorderColor(new XSSFColor(Color.BLACK));
        itemCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        itemCellStyle.setTopBorderColor(new XSSFColor(Color.BLACK));
        itemCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        itemCellStyle.setBottomBorderColor(new XSSFColor(Color.BLACK));
        itemCellStyle.setAlignment(HorizontalAlignment.LEFT);
    }

    private void initNumericItemCellStyle(XSSFWorkbook workbook) {
        numericItemCellStyle = workbook.createCellStyle();
        numericItemCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        numericItemCellStyle.setLeftBorderColor(new XSSFColor(Color.BLACK));
        numericItemCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        numericItemCellStyle.setRightBorderColor(new XSSFColor(Color.BLACK));
        numericItemCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        numericItemCellStyle.setTopBorderColor(new XSSFColor(Color.BLACK));
        numericItemCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        numericItemCellStyle.setBottomBorderColor(new XSSFColor(Color.BLACK));
        numericItemCellStyle.setAlignment(HorizontalAlignment.LEFT);
        numericItemCellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        numericItemCellStyle.setAlignment(HorizontalAlignment.CENTER);
    }

    private void generateHeader(XSSFSheet sheet) {
        sheet.createFreezePane(0, 1);
        sheet.setColumnWidth(0, 15*256);
        sheet.setColumnWidth(1, 85*256);
        sheet.setColumnWidth(2, 10*256);
        sheet.setColumnWidth(3, 10*256);
        sheet.setColumnWidth(4, 10*256);
        sheet.setColumnWidth(5, 10*256);
        sheet.setColumnWidth(6, 10*256);
        sheet.setColumnWidth(7, 10*256);
        sheet.setColumnWidth(8, 60*256);
        XSSFRow row = sheet.createRow(rowNumber);
        row.setHeight((short)(3*256));
        XSSFCell cell;
        for(int i=0; i<9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            switch(i) {
                case 0:
                    cell.setCellValue("GSD");
                    break;
                case 1:
                    cell.setCellValue("ProjectName");
                    break;
                case 2:
                    cell.setCellValue("Estimated Effort");
                    break;
                case 3:
                    cell.setCellValue("Real Effort Spend");
                    break;
                case 4:
                    cell.setCellValue("Developer");
                    break;
                case 5:
                    cell.setCellValue("Rest Eff.");
                    break;
                case 6:
                    cell.setCellValue("Pend. Eff.");
                    break;
                case 7:
                    cell.setCellValue("Underestm. Eff.");
                    break;
                case 8:
                    cell.setCellValue("Comment");
                    break;
            }
        }
        rowNumber++;
    }

    private void generateRegions(XSSFSheet sheet) {
        ProjectStructureBean[] psbList = ProjectDAO.getProejctStructure();
        for(ProjectStructureBean psb:psbList) {
            XSSFRow row = sheet.createRow(rowNumber);
            for(int i=0; i<9; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(regionStyle);
                switch(i) {
                    case 0:
                        cell.setCellValue(psb.getRegion());
                        sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), 0, 8));
                        break;
                }
            }
            rowNumber++;
            for(String type:psb.getTypes()) {
                row = sheet.createRow(rowNumber);
                rowNumber++;
                insertItems(psb.getRegion(), type, sheet);
                for (int i = 0; i < 9; i++) {
                    XSSFCell cell = row.createCell(i);
                    switch (i) {
                        case 0:
                            cell.setCellStyle(subTypeStyle);
                            cell.setCellValue(type);
                            sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), 0, 1));
                            break;
                        case 1:
                        case 4:
                        case 8:
                            cell.setCellStyle(subTypeStyle);
                            break;
                        case 2:
                            cell.setCellStyle(numericSubTypeStyle);
                            cell.setCellFormula("SUM(C" + (cell.getRowIndex() + 2) + ":C" + rowNumber + ")");
                            break;
                        case 3:
                            cell.setCellStyle(numericSubTypeStyle);
                            cell.setCellFormula("SUM(D" + (cell.getRowIndex() + 2) + ":D" + rowNumber + ")");
                            break;
                        case 5:
                            cell.setCellStyle(numericSubTypeStyle);
                            cell.setCellFormula("SUM(F" + (cell.getRowIndex() + 2) + ":F" + rowNumber + ")");
                            break;
                        case 6:
                            cell.setCellStyle(numericSubTypeStyle);
                            cell.setCellFormula("SUM(G" + (cell.getRowIndex() + 2) + ":G" + rowNumber + ")");
                            break;
                        case 7:
                            cell.setCellStyle(numericSubTypeStyle);
                            cell.setCellFormula("SUM(H" + (cell.getRowIndex() + 2) + ":H" + rowNumber + ")");
                            break;
                    }
                }
            }
        }
    }

    private void insertItems(String region, String type, XSSFSheet sheet) {
        if(projectList == null || projectList.size() == 0) {
            rowNumber++;
        }
        boolean noValue = true;
        for(ProjectBean pb:projectList) {
            if(StringUtils.equals(region,pb.getRegion()) && StringUtils.equals(type, pb.getItem_Type())) {
                noValue = false;
                XSSFRow row = sheet.createRow(rowNumber);
                rowNumber++;
                for(int i=0; i<9; i++) {
                    XSSFCell cell = row.createCell(i);
                    switch(i) {
                        case 0:
                            cell.setCellStyle(itemCellStyle);
                            cell.setCellValue(pb.getGSD());
                            break;
                        case 1:
                            cell.setCellStyle(itemCellStyle);
                            cell.setCellValue(pb.getProjectName());
                            break;
                        case 2:
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellStyle(numericItemCellStyle);
                            if(pb.getEstimateEffort() != 0) {
                                cell.setCellValue((double)pb.getEstimateEffort());
                            }
                            break;
                        case 3:
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellStyle(numericItemCellStyle);
                            if(pb.getRealEffort() != 0) {
                                cell.setCellValue(pb.getRealEffort());
                            }
                            break;
                        case 4:
                            cell.setCellStyle(numericItemCellStyle);
                            cell.setCellValue(pb.getDeveloper());
                            break;
                        case 5:
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellStyle(numericItemCellStyle);
                            if(pb.getRestEffort() != 0) {
                                cell.setCellValue(pb.getRestEffort());
                            }
                            break;
                        case 6:
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellStyle(numericItemCellStyle);
                            if(pb.getPendingEffort() != 0) {
                                cell.setCellValue(pb.getPendingEffort());
                            }
                            break;
                        case 7:
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellStyle(numericItemCellStyle);
                            if(pb.getUnderEstimatedEffort() != 0) {
                                cell.setCellValue(pb.getUnderEstimatedEffort());
                            }
                            break;
                        case 8:
                            cell.setCellStyle(itemCellStyle);
                            cell.setCellValue(pb.getItem_Comment());
                            break;
                    }
                }
            }
        }
        if(noValue) {
            int j = 0;
            while(j<3) {
                XSSFRow row = sheet.createRow(rowNumber);
                rowNumber++;
                for (int i = 0; i < 9; i++) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellStyle(itemCellStyle);
                }
                j++;
            }
        }
    }

    public static ArrayList<ProjectBean> phaseScriptFile(InputStream fileStream) throws IOException {
        ArrayList<ProjectBean> result  = new ArrayList<ProjectBean>();
        HSSFWorkbook workbook = new HSSFWorkbook(fileStream);
        HSSFSheet sheet = workbook.getSheet("Export Project Data");
        for(int i=1; i<=sheet.getLastRowNum(); i++) {
            ProjectBean pb = new ProjectBean();
            HSSFRow row = sheet.getRow(i);
            if(StringUtils.isEmpty(getCellValue(row.getCell(0))) && StringUtils.isNotEmpty(getCellValue(row.getCell(1)))){
                if("Corporate SWORD".equals(getCellValue(row.getCell(0)))) {
                    pb.setRegion("CORPORATE");
                } else if("APAC SWORD".equals(getCellValue(row.getCell(0)))) {
                    pb.setRegion("APAC");
                }
                pb.setScriptId(getCellValue(row.getCell(2)));
                pb.setGSD(getCellValue(row.getCell(3)));
                pb.setProjectName(getCellValue(row.getCell(5)));
                pb.setCustomer(getCellValue(row.getCell(6)));
                pb.setCategory(getCellValue(row.getCell(10)));
                pb.setCoordinator(getCellValue(row.getCell(11)));
                pb.setPriority(getCellValue(row.getCell(12)));
                if(NumberUtils.toInt(getCellValue(row.getCell(19))) != 0) {
                    pb.setLiveOnTest(getDateFromDays(NumberUtils.toInt(getCellValue(row.getCell(19)))));
                }
                if(NumberUtils.toInt(getCellValue(row.getCell(24))) != 0) {
                    pb.setLiveOnProd(getDateFromDays(NumberUtils.toInt(getCellValue(row.getCell(24)))));
                }
                pb.setDeveloper(getCellValue(row.getCell(25)));
                pb.setEstimateEffort(NumberUtils.toFloat(getCellValue(row.getCell(32))));
                pb.setDocLink(getCellValue(row.getCell(40)));
                pb.setMaps(NumberUtils.toInt(getCellValue(row.getCell(51)), 0));
                pb.setItem_Type("Planned");
                result.add(pb);
            } else if(StringUtils.isNotEmpty(getCellValue(row.getCell(0)))){
                if("Corporate SWORD".equals(getCellValue(row.getCell(0)))) {
                    pb.setRegion("CORPORATE");
                } else if("APAC SWORD".equals(getCellValue(row.getCell(0)))) {
                    pb.setRegion("APAC");
                }
                pb.setScriptId(getCellValue(row.getCell(1)));
                pb.setGSD(getCellValue(row.getCell(2)));
                pb.setProjectName(getCellValue(row.getCell(4)));
                pb.setCustomer(getCellValue(row.getCell(5)));
                pb.setCategory(getCellValue(row.getCell(9)));
                pb.setCoordinator(getCellValue(row.getCell(10)));
                pb.setPriority(getCellValue(row.getCell(11)));
                if(NumberUtils.toInt(getCellValue(row.getCell(18))) != 0) {
                    pb.setLiveOnTest(getDateFromDays(NumberUtils.toInt(getCellValue(row.getCell(18)))));
                }
                if(NumberUtils.toInt(getCellValue(row.getCell(23))) != 0) {
                    pb.setLiveOnProd(getDateFromDays(NumberUtils.toInt(getCellValue(row.getCell(23)))));
                }
                pb.setDeveloper(getCellValue(row.getCell(24)));
                pb.setEstimateEffort(NumberUtils.toFloat(getCellValue(row.getCell(31))));
                pb.setDocLink(getCellValue(row.getCell(39)));
                pb.setMaps(NumberUtils.toInt(getCellValue(row.getCell(50)), 0));
                pb.setItem_Type("Planned");
                result.add(pb);
            }
        }
        return result;
    }

    private static String getPorjectListFilePath(){
        return SystemConf.getConfString("server.project.path");
    }

    private static String getDateFromDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.YEAR, 1899);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DATE, 30);
        cal.add(Calendar.DATE, days);
        return DateFormatUtils.format(cal, "yyyy-MM-dd");
    }

    private static String getCellValue(HSSFCell cell) {
        if(cell==null) {
            return "";
        }
        String result;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
            case HSSFCell.CELL_TYPE_BLANK:
                result = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                result = String.valueOf(cell.getErrorCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result = String.valueOf(cell.getCellFormula());
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                DecimalFormat decimalFormat = new DecimalFormat("###.###");
                result = String.valueOf(decimalFormat.format(cell.getNumericCellValue()));
                break;
            default:
                result = "";
                break;
        }
        if("null".equals(StringUtils.trim(result))) {
            return "";
        } else {
            return result;
        }
    }

}
