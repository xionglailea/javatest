package TestFile;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ²âÊÔexcel¶ÁÐ´
 *
 * <p>
 * create by xiongjieqing on 2021/3/2 15:00
 */
public class TestExcel {

    public static void main(String[] args) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet();

        sheet.setColumnWidth(0,256 * 3);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);  //Ìî³äµ¥Ôª¸ñ
        cellStyle.setFillForegroundColor(HSSFColor.RED.index);    //ÌîºìÉ«
        cellStyle.setAlignment((short) HorizontalAlignment.CENTER.ordinal());
        var row = sheet.createRow(0);
        var cell1 = row.createCell(0);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue(10);


        FileOutputStream temp = new FileOutputStream(new File("temp.xlsx"));
        workbook.write(temp);
    }

}
