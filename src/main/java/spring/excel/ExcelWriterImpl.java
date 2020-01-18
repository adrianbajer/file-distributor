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
        String filePath = "src\\main\\resources\\excelfiles\\excel example file.xls";

        try {
            file = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            workbook = new HSSFWorkbook(file);

            Sheet cdrFiles = workbook.getSheetAt(1);

            for(Row row : cdrFiles) {
                if (row.getRowNum() > 1) {
                    String cellValue = row.getCell(0).getStringCellValue();
                    for(CdrFile cdrFile : cdrFilesSet) {
                        if(cellValue.equals(cdrFile.getName())) {
                            row.getCell(3).setCellValue(raksCode.getUserName().getName());
                            break;
                        }
                    }
                }
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
