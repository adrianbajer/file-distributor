package spring.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class ExcelParserImpl implements ExcelParser{


    @Override
    public Set<CdrFile> getSetOfCdrFiles(RaksCode raksCode) {
        Workbook workbook = null;
        FileInputStream file = null;
        String raksCodeName = raksCode.getRaksCode();


        List <String> actualisationAreasList = new ArrayList<>();
        Set <CdrFile> cdrFilesSet = new HashSet<>();


        try {
            file = new FileInputStream(new File("src\\main\\resources\\excelfiles\\excel example file.xls"));
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


//            @@@@@@@@@@@@@ This loop gets names of "actualisation areas" from "publications" sheet,
//            which has RaksCode equal to RaksCode given by user @@@@@@@@@@@@@

            for(Row row : publications) {
                // first condition skips 3 first rows, which contains headings
                if (row.getRowNum() > 2) {
                    if (row.getCell(0).toString().equals(raksCodeName)) {
                        actualisationAreasList.add(row.getCell(11).toString());
                    }
                }
            }

//         @@@@@@@@@@@@@ This loop creates instances of CdrFile class and sets their attributes.
//         Attributes are taken from "actualisationAreas" sheet, basing on "actualisationAreasList" values,
//         which are primary key in that sheet. Set is used to get unique instances of CdrFile. @@@@@@@@@@@@@
            for(Row row : actualisationAreas) {
                if (row.getRowNum() > 1) {
                    String cellValue = row.getCell(0).getStringCellValue();
                    for(String actualisationArea : actualisationAreasList) {
                        if(cellValue.equals(actualisationArea)) {
//                            cdrFilesSet.add(new CdrFile(row.getCell(1).toString(),"",
//                                    row.getCell(15).toString(),row.getCell(16).toString()));
                            break;
                        }
                    }
                }
            }

//            @@@@@@@@@@@@ This loop sets place attribute in CdrFile objects @@@@@@@@@@@@@

            for(Row row : cdrFiles) {
                if (row.getRowNum() > 1) {
                    String cellValue = row.getCell(0).getStringCellValue();
                    for(CdrFile cdrFile : cdrFilesSet) {
                        if(cellValue.equals(cdrFile.getName())) {
                            cdrFile.setPlace(row.getCell(3).toString());
                            break;
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

    @Override
    public Set<CdrFile> getSetOfCdrFilesFromUploadedFile(MultipartFile file, RaksCode raksCode) {

        Set<CdrFile> cdrFilesSet = raksCode.getCdrFileSet();

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet cdrFilesFromUploadedFile = workbook.getSheetAt(0);

            int i = 1;
            for (CdrFile cdrFile : cdrFilesSet){
                Row row = cdrFilesFromUploadedFile.getRow(i);
                cdrFile.setName(row.getCell(0).toString());
                cdrFile.setPlace(row.getCell(1).toString());
                cdrFile.setRegion(row.getCell(2).toString());
                cdrFile.setType(row.getCell(3).toString());
                i++;
            }

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cdrFilesSet;
    }

//    @Override
//    public Set<CdrFile> readDataFromFile(MultipartFile file) {
//        try {
//            InputStream inputStream = file.getInputStream();
//            Workbook workbook = new HSSFWorkbook(inputStream);
//            Sheet cdrFilesFromUploadedFile = workbook.getSheetAt(0);
//            Set <CdrFile> cdrFilesSet = new HashSet<>();
//
//            for(Row row : cdrFilesFromUploadedFile) {
//                if (row.getRowNum() > 1) {
//                    cdrFilesSet.add(new CdrFile(row.getCell(1).toString(),"",
//                                    row.getCell(15).toString(),row.getCell(16).toString()));
//
//
//            Sheet cdrFilesDataToUpdate = workbook.getSheetAt(0);
//            String cellValueFromUploadedFile = cdrFilesDataToUpdate.getRow(2).getCell(0).getStringCellValue();
//
//            System.out.println(cellValueFromUploadedFile);
//
//            inputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return cdrFilesSet;
//    }
}

