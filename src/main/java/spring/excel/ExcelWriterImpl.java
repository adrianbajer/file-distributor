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
            String filename = raksCode.getRaksCode() + ".xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet cdrFilesSheet = workbook.createSheet("cdrFiles");
            Row headRow = cdrFilesSheet.createRow(0);

            headRow.createCell(0).setCellValue("File name");
            headRow.createCell(1).setCellValue("Employee");
            headRow.createCell(2).setCellValue("Region");
            headRow.createCell(3).setCellValue("Type");
//            headRow.createCell(4).setCellValue("Job type");

            int i = 1;
            for(CdrFile cdrFile : cdrFilesSet) {
                Row row = cdrFilesSheet.createRow(i);
                row.createCell(0).setCellValue(cdrFile.getName());
                row.createCell(1).setCellValue(cdrFile.getPlace());
                row.createCell(2).setCellValue(cdrFile.getRegion());
                row.createCell(3).setCellValue(cdrFile.getType());
//                row.createCell(4).setCellValue(raksCode.getJobType().getType());
                i++;
            }

            Row row = cdrFilesSheet.createRow(i + 1);
            row.createCell(0).setCellValue("To update database, change the values above (except File name) and upload this excel file.");
//            row.createCell(0).setCellValue("You may change values above. Then upload this xls file. Appropriate data will be changed in database.");

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
