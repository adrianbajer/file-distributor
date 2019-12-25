package spring.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import spring.plikizrodlowe.PlikZrodlowy;
import spring.rakscode.RaksCode;

import java.io.*;
import java.util.*;

public class ExcelParserImpl implements ExcelParser{


    @Override
    public Set<PlikZrodlowy> getSetOfPlikiZrodlowe(RaksCode raksCode) {
        Workbook workbook = null;
        FileInputStream file = null;
        String raksCodeName = raksCode.getRaksCode();

        List <String> zasiegiKorektyList = new ArrayList<>();
        Set <PlikZrodlowy> plikiZrodloweSet = new HashSet<>();


        try {
            file = new FileInputStream(new File("C:\\Users\\Adrian\\Documents\\Nauka programowania\\" +
                    "FileDistributor_materiały\\pliki xls\\Baza archiwum FD.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {

            // @@@@@@@@@@ XSSFWorkbook for xlsx files, HSSFWorkbook for xls files @@@@@@@@@@@@@@@
//            workbook = new XSSFWorkbook(file);
            workbook = new HSSFWorkbook(file);

            Sheet wydania = workbook.getSheetAt(0);
            Sheet plikiZrodlowe = workbook.getSheetAt(1);
            Sheet zasiegiKorekty = workbook.getSheetAt(2);


//            @@@@@@@@@@@@@ this loop gets names of "zasiegi korekty" from "wydania" sheet,
//            which has RaksCode equal to RaksCode given by user @@@@@@@@@@@@@

            for(Row row : wydania) {
                // first condition skips 3 first rows, which contains headings
                if (row.getRowNum() > 2) {
                    if (row.getCell(0).toString().equals(raksCodeName)) {
                        zasiegiKorektyList.add(row.getCell(11).toString());
                    }
                }
            }

//         @@@@@@@@@@@@@ this loop creates of PlikZrodlowy class and sets their attributes from "zasiegi_korekty" sheet,
//            basing on "zasiegiKorektyList" values, which are primary key in that sheet.
//            Set is used to get unique instances of PlikZrodlowy. @@@@@@@@@@@@@
            for(Row row : zasiegiKorekty) {
                if (row.getRowNum() > 1) {
                    String cellValue = row.getCell(0).getStringCellValue();
                    for(String zasiegKorekty : zasiegiKorektyList) {
                        if(cellValue.equals(zasiegKorekty)) {
                            plikiZrodloweSet.add(new PlikZrodlowy(row.getCell(1).toString(),"",
                                    row.getCell(15).toString(),row.getCell(16).toString()));
                            break;
                        }
                    }
                }
            }

//            @@@@@@@@@@@@ this loop sets place name attribute in PlikZrodlowy objects @@@@@@@@@@@@@

            for(Row row : plikiZrodlowe) {
                if (row.getRowNum() > 1) {
                    String cellValue = row.getCell(0).getStringCellValue();
                    for(PlikZrodlowy plikZrodlowy : plikiZrodloweSet) {
                        if(cellValue.equals(plikZrodlowy.getName())) {
                            plikZrodlowy.setPlace(row.getCell(3).toString());
                            break;
                        }
                    }
                }
            }


            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return plikiZrodloweSet;
    }
}
