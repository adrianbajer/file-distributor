package spring.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.io.*;
import java.util.Set;

public class ExcelWriterImpl implements ExcelWriter {


    @Override
    public File createAndFillExcelFile(Set<CdrFile> cdrFilesSet, RaksCode raksCode) {

        try {
            String filename = "NewExcelFile.xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet cdrFilesSheet = workbook.createSheet("cdrFiles");
            Row headRow = cdrFilesSheet.createRow(0);

            headRow.createCell(0).setCellValue("File name");
            headRow.createCell(1).setCellValue("Employee name");
            headRow.createCell(2).setCellValue("Region");
            headRow.createCell(3).setCellValue("File type");
            headRow.createCell(4).setCellValue("Job type");

            int i = 1;
            for(CdrFile cdrFile : cdrFilesSet) {
                Row row = cdrFilesSheet.createRow(i);
                row.createCell(0).setCellValue(cdrFile.getName());
                row.createCell(1).setCellValue(raksCode.getUserName().getName());
                row.createCell(2).setCellValue(cdrFile.getRegion());
                row.createCell(3).setCellValue(cdrFile.getType());
                row.createCell(4).setCellValue(raksCode.getJobType().getType());
                i++;
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            File file = new File(filename);

            return file;

        } catch ( IOException ex ) {
            System.out.println(ex);
        }

        return null;
    }
}
