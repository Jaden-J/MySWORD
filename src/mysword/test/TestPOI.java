package mysword.test;

import mysword.bean.project.ProjectBean;
import mysword.bean.project.ProjectStructureBean;
import mysword.dao.project.ProjectDAO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2014/7/30.
 */
public class TestPOI {

    private int rowNumber = 0;
    public XSSFWorkbook workbook;
    private ArrayList<ProjectBean> projectList;
    public XSSFCellStyle headerStyle;
    public XSSFCellStyle regionStyle;
    public XSSFCellStyle subTypeStyle;
    public XSSFCellStyle itemCellStyle;
    public XSSFCellStyle cardStyle;

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        TestPOI test = new TestPOI();
//        test.projectList = ProjectDAO.getProjects(null, null, null, null, null, null, null, null, null, -2, -2);
//        test.generateExcel();
        test.generateWord();
    }

    public void generateWord() throws IOException {
        File file = new File("d:/test.xlsx");
        FileOutputStream fOut = null;
        workbook = new XSSFWorkbook();
        initProjectCardStyle(workbook);
        XSSFSheet sheet = workbook.createSheet("Test");
        sheet.setMargin(XSSFSheet.TopMargin,0);
        sheet.setMargin(XSSFSheet.BottomMargin,0);
        sheet.setMargin(XSSFSheet.LeftMargin,0);
        sheet.setMargin(XSSFSheet.RightMargin,0);
        sheet.setColumnWidth(0, 42*256);
        sheet.setColumnWidth(1, 2*256);
        sheet.setColumnWidth(2, 42*256);
        insertCard(sheet, new String[]{"[1] /# 1374982: Merck BRS - Update Routing (XIB TEST)", "2", "3", "4", "5", "6", "7", "8"});
        fOut = new FileOutputStream(file);
        workbook.write(fOut);
        fOut.flush();
        fOut.close();
    }

    private void insertCard(XSSFSheet sheet, String[] value) {
        int rowIndex = 0;
        int cellIndex = 0;
        XSSFRow row = sheet.createRow(rowIndex);
        row.setHeight((short)(16*256));
        rowIndex++;
        for(int i=0; i<value.length; i++) {
            if(i!=0&&i%2==0) {
                row = sheet.createRow(rowIndex);
                row.setHeight((short)(0.5*256));
                rowIndex++;
                row = sheet.createRow(rowIndex);
                row.setHeight((short)(16*256));
                rowIndex++;
            }
            XSSFCell cell = row.createCell(cellIndex);
            if(cellIndex==0) {
                cellIndex += 2;
            } else {
                cellIndex = 0;
            }
            cell.setCellStyle(cardStyle);
            cell.setCellValue(value[i]);
        }
    }

    private void initProjectCardStyle(XSSFWorkbook workbook) {
        cardStyle = workbook.createCellStyle();
        cardStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeight((short)360);
        cardStyle.setFont(font);
        cardStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        cardStyle.setLeftBorderColor(new XSSFColor(Color.BLACK));
        cardStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        cardStyle.setRightBorderColor(new XSSFColor(Color.BLACK));
        cardStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        cardStyle.setTopBorderColor(new XSSFColor(Color.BLACK));
        cardStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        cardStyle.setBottomBorderColor(new XSSFColor(Color.BLACK));
        cardStyle.setAlignment(HorizontalAlignment.LEFT);
        cardStyle.setVerticalAlignment(VerticalAlignment.TOP);
        cardStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)200, (byte)200, (byte)200}));
        cardStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }

    public void generateExcel() throws IOException {
        File file = new File("e://Downloads/test.xlsx");
        FileOutputStream fOut = null;

        workbook = new XSSFWorkbook();
        initHeaderStyle(workbook);
        initRegionStyle(workbook);
        initSubTypeStyle(workbook);
        initItemCellStyle(workbook);
        XSSFSheet sheet = workbook.createSheet("Test");
        generateHeader(sheet);
        generateRegions(sheet);
        fOut = new FileOutputStream(file);
        workbook.write(fOut);
        fOut.flush();
        fOut.close();
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
        headerStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)200, (byte)200, (byte)200}));
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }

    private void initRegionStyle(XSSFWorkbook workbook) {
        regionStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        regionStyle.setFont(font);
        regionStyle.setAlignment(HorizontalAlignment.CENTER);
        regionStyle.setBorderTop(CellStyle.BORDER_DOUBLE);
        regionStyle.setTopBorderColor(new XSSFColor(Color.GREEN));
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
        subTypeStyle.setDataFormat(workbook.createDataFormat().getFormat("#,###0.000"));
        subTypeStyle.setFont(font);
        subTypeStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)150, (byte)150, (byte)150}));
        subTypeStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
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
                    cell.setCellStyle(subTypeStyle);
                    switch (i) {
                        case 0:
                            cell.setCellValue(type);
                            sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), 0, 1));
                            break;
                        case 2:
                            cell.setCellFormula("SUM(C" + (cell.getRowIndex() + 2) + ":C" + rowNumber + ")");
                            break;
                        case 3:
                            cell.setCellFormula("SUM(D" + (cell.getRowIndex() + 2) + ":D" + rowNumber + ")");
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
                    cell.setCellStyle(itemCellStyle);
                    switch(i) {
                        case 0:
                            cell.setCellValue(pb.getGSD());
                            break;
                        case 1:
                            cell.setCellValue(pb.getProjectName());
                            break;
                        case 2:
                            if(pb.getEstimateEffort() != 0) {
                                cell.setCellValue(pb.getEstimateEffort());
                            }
                            break;
                        case 3:
                            if(pb.getRealEffort() != 0) {
                                cell.setCellValue(pb.getRealEffort());
                            }
                            break;
                        case 4:
                            cell.setCellValue(pb.getDeveloper());
                            break;
                        case 5:
                            if(pb.getRestEffort() != 0) {
                                cell.setCellValue(pb.getRestEffort());
                            }
                            break;
                        case 6:
                            if(pb.getPendingEffort() != 0) {
                                cell.setCellValue(pb.getPendingEffort());
                            }
                            break;
                        case 7:
                            if(pb.getUnderEstimatedEffort() != 0) {
                                cell.setCellValue(pb.getUnderEstimatedEffort());
                            }
                            break;
                        case 8:
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
}
