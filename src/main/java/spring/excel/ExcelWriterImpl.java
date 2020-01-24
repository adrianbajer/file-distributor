package spring.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.io.*;
import java.util.Set;

public class ExcelWriterImpl implements ExcelWriter {


    @Override
    public void saveChangesToExcelFile(Set<CdrFile> cdrFilesSet, RaksCode raksCode) {

        Workbook workbook = null;
        FileInputStream file = null;
        String filePath = "src\\main\\resources\\excelfiles\\downloaded_files_data.xls";

        try {
            file = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            workbook = new HSSFWorkbook(file);

            Sheet cdrFilesSheet = workbook.getSheetAt(0);

            int i = 1;
            for(CdrFile cdrFile : cdrFilesSet) {
                Row row = cdrFilesSheet.createRow(i);
                row.createCell(0).setCellValue(cdrFile.getName());
                row.createCell(1).setCellValue(raksCode.getUserName().getName());
                row.createCell(2).setCellValue(cdrFile.getRegion());
                row.createCell(3).setCellValue(cdrFile.getType());
                row.createCell(4).setCellValue(raksCode.getJobType().toString());
                i++;
            }

            file.close();

            FileOutputStream outFile = new FileOutputStream(filePath);
            workbook.write(outFile);
            outFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
