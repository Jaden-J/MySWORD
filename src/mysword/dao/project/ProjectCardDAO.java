package mysword.dao.project;

import mysword.bean.project.ProjectBean;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by hyao on 8/8/2014.
 */
public class ProjectCardDAO {

    public XSSFWorkbook workbook;
    public XSSFCellStyle defaultCardStyle;
    public XSSFCellStyle apacCardStyle;

    public ByteArrayInputStream generateCardExcel(ProjectBean... projectList) throws IOException {
        if(projectList==null || projectList.length==0) {
            return new ByteArrayInputStream(new byte[]{});
        }
        workbook = new XSSFWorkbook();
        initProjectCardStyle(workbook);
        XSSFSheet sheet = workbook.createSheet("Test");
        sheet.setMargin(XSSFSheet.TopMargin,0);
        sheet.setMargin(XSSFSheet.BottomMargin,0);
        sheet.setMargin(XSSFSheet.LeftMargin, 0);
        sheet.setMargin(XSSFSheet.RightMargin, 0);
        sheet.setColumnWidth(0, 45*256);
        sheet.setColumnWidth(1, 1*256);
        sheet.setColumnWidth(2, 45*256);
        insertCard(sheet, projectList);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void insertCard(XSSFSheet sheet, ProjectBean... projectList) {
        int rowIndex = 0;
        int cellIndex = 0;
        XSSFRow row = sheet.createRow(rowIndex);
        row.setHeight((short)(18*256));
        rowIndex++;
        String[] cardStrings = new String[projectList.length];
        for(int i=0; i<projectList.length; i++) {
            if(i!=0&&i%2==0) {
                row = sheet.createRow(rowIndex);
                row.setHeight((short)(0.5*256));
                rowIndex++;
                row = sheet.createRow(rowIndex);
                row.setHeight((short)(18*256));
                rowIndex++;
            }
            XSSFCell cell = row.createCell(cellIndex);
            if(cellIndex==0) {
                cellIndex += 2;
            } else {
                cellIndex = 0;
            }
            if("APAC".equals(projectList[i].getRegion())){
                cell.setCellStyle(apacCardStyle);
            } else {
                cell.setCellStyle(defaultCardStyle);
            }
            String cardString = "["+(i+1)+"][W"+ StringUtils.substring(projectList[i].getWeek(), 5)+"] "+projectList[i].getScriptId()+"/#"+projectList[i].getGSD()+": "+projectList[i].getProjectName()+"\nCon: "+projectList[i].getCoordinator()+", Effort: "+projectList[i].getEstimateEffort()+", Maps: "+projectList[i].getMaps()+", OnQA: "+projectList[i].getLiveOnTest()+", OnProd: "+projectList[i].getLiveOnProd();
            cell.setCellValue(cardString);
        }
    }

    private void initProjectCardStyle(XSSFWorkbook workbook) {
        defaultCardStyle = workbook.createCellStyle();
        defaultCardStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeight((short) 360);
        font.setColor(new XSSFColor(Color.WHITE));
        defaultCardStyle.setFont(font);
        defaultCardStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        defaultCardStyle.setLeftBorderColor(new XSSFColor(Color.BLACK));
        defaultCardStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        defaultCardStyle.setRightBorderColor(new XSSFColor(Color.BLACK));
        defaultCardStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        defaultCardStyle.setTopBorderColor(new XSSFColor(Color.BLACK));
        defaultCardStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        defaultCardStyle.setBottomBorderColor(new XSSFColor(Color.BLACK));
        defaultCardStyle.setAlignment(HorizontalAlignment.LEFT);
        defaultCardStyle.setVerticalAlignment(VerticalAlignment.TOP);
        XSSFFont fontAPAC = workbook.createFont();
        fontAPAC.setFontName("Arial");
        fontAPAC.setBold(true);
        fontAPAC.setFontHeight((short) 360);
        fontAPAC.setColor(new XSSFColor(new byte[]{(byte)255, (byte)255, (byte)255}));
        apacCardStyle = (XSSFCellStyle)defaultCardStyle.clone();
        apacCardStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)210, (byte)210, (byte)210}));
        apacCardStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        apacCardStyle.setFont(fontAPAC);
    }
}
