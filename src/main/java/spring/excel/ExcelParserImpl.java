package spring.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ExcelParserImpl implements ExcelParser{

    private String pathName;

    public ExcelParserImpl() {
    }

    @Autowired
    public ExcelParserImpl(@Qualifier("bazaArchiwumPath") String pathName) {
        this.pathName = pathName;
    }

    public String getExcelFileData() {
        Workbook workbook = null;
        FileInputStream file = null;

        try {
            file = new FileInputStream(new File(pathName));
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
            file = new FileInputStream(new File(pathName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {

            // @@@@@@@@@@ XSSFWorkbook for xlsx files, HSSFWorkbook for xls files @@@@@@@@@@@@@@@
//            workbook = new XSSFWorkbook(file);
            workbook = new HSSFWorkbook(file);

            Date dateOfCdrFile;
            SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
            Sheet publications = workbook.getSheetAt(0);
            Sheet cdrFiles = workbook.getSheetAt(1);
            Sheet actualisationAreas = workbook.getSheetAt(2);


            for(Row row : publications) {
                // first condition skips 3 first rows, which contains headings
                if (row.getRowNum() > 3) {
                    if(row.getCell(0) != null){
                        if (row.getCell(0).toString().equals(raksCodeName)) {
                            dateOfCdrFile = row.getCell(22).getDateCellValue();
                            String formattedDateOfCdrFile = formatter.format(dateOfCdrFile);

                            cdrFilesSet.add(new CdrFile(row.getCell(20).getRichStringCellValue().toString(), row.getCell(21).getRichStringCellValue().toString(),
                                    formattedDateOfCdrFile, row.getCell(23).getRichStringCellValue().toString(),
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
            file = new FileInputStream(new File(pathName));
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
        if (raksCodeList.get(0).getRaksCode().equals(""))
        raksCodeList.remove(0);

        return raksCodeList;
    }
}
