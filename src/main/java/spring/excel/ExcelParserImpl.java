package spring.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.io.*;
import java.util.*;

public class ExcelParserImpl implements ExcelParser{

    private final String PATH_NAME = "C:\\fd\\Baza archiwum AB.xls";
//    private final String PATH_NAME = "src\\main\\resources\\excelfiles\\excel example file.xls";

    public ExcelParserImpl() {
    }

    public String getExcelFileData() {
        Workbook workbook = null;
        FileInputStream file = null;

        try {
            file = new FileInputStream(new File(PATH_NAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            workbook = new HSSFWorkbook(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workbook.getSheetAt(0).getSheetName();
    }

//-------------------------------------------------------------------------

    @Override
    public Set<CdrFile> getSetOfCdrFiles(RaksCode raksCode) {
        Workbook workbook = null;
        FileInputStream file = null;
        String raksCodeName = raksCode.getRaksCode();


        List <String> actualisationAreasList = new ArrayList<>();
        Set <CdrFile> cdrFilesSet = new HashSet<>();


        try {
            file = new FileInputStream(new File(PATH_NAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {

            // @@@@@@@@@@ XSSFWorkbook for xlsx files, HSSFWorkbook for xls files @@@@@@@@@@@@@@@
//            workbook = new XSSFWorkbook(file);
            workbook = new HSSFWorkbook(file);

            Sheet publications = workbook.getSheetAt(0);
            Sheet cdrFiles = workbook.getSheetAt(1);
            Sheet actualisationAreas = workbook.getSheetAt(2);


            for(Row row : publications) {
                // first condition skips 3 first rows, which contains headings
                if (row.getRowNum() > 3) {
                    if(row.getCell(0) != null){
                        if (row.getCell(0).toString().equals(raksCodeName)) {
                            cdrFilesSet.add(new CdrFile(row.getCell(20).getRichStringCellValue().toString(),row.getCell(23).getRichStringCellValue().toString(),
                                    "","",row.getCell(25).getRichStringCellValue().toString()));
                        }
                    }
                }
            }

            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cdrFilesSet;
    }

//-------------------------------------------------------------------------

    public List<RaksCode> getListOfRaksCodes() {
        Workbook workbook = null;
        FileInputStream file = null;

        Set <RaksCode> raksCodeSet = new HashSet<>();


        try {
            file = new FileInputStream(new File(PATH_NAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {

            // @@@@@@@@@@ XSSFWorkbook for xlsx files, HSSFWorkbook for xls files @@@@@@@@@@@@@@@
//            workbook = new XSSFWorkbook(file);
            workbook = new HSSFWorkbook(file);

            Sheet publications = workbook.getSheetAt(0);


            for(Row row : publications) {
                // first condition skips 3 first rows, which contains headings
                if (row.getRowNum() > 2) {
                    if (row.getCell(0) != null) {
                        raksCodeSet.add(new RaksCode(row.getCell(0).toString()));
                    }
                }
            }

            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<RaksCode> raksCodeList = new ArrayList<>(raksCodeSet);
        Collections.sort(raksCodeList);

        return raksCodeList;
    }
}
