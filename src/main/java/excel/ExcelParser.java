package excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.*;

public class ExcelParser {

    public static void main(String[] args) {

        Workbook workbook = null;
        FileInputStream file = null;
        String userInputRaksCode = "Akwitania._map_LAM9_FR";

        List <String> zasiegiKorektyList = new ArrayList<>();
        Set <String> plikiZrodloweSet = new HashSet<>();
        Map<String, String> plikiZrodloweRegionMap = new HashMap<>();
        Map<String, String> plikiZrodloweRodzajMap = new HashMap<>();
        Map<String, String> plikiZrodloweMiejsceMap = new HashMap<>();


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
                    if (row.getCell(0).toString().equals(userInputRaksCode)) {
                        zasiegiKorektyList.add(row.getCell(11).toString());
                    }
                }
            }

//         @@@@@@@@@@@@@ this loop gets names of "pliki zrodlowe" from "zasiegi_korekty" sheet,
//            basing on "zasiegiKorektyList" values, which are primary key in that sheet.
//            Set is used to get unique names of "pliki zrodlowe".
//            There are also created two Maps, which contains data for preparing path to files.
//            Maps will be used later in the FileDistributor application @@@@@@@@@@@@@
            for(Row row : zasiegiKorekty) {
                if (row.getRowNum() > 1) {
                    String cellValue = row.getCell(0).getStringCellValue();
                    for(String zasiegKorekty : zasiegiKorektyList) {
                        if(cellValue.equals(zasiegKorekty)) {
                            plikiZrodloweSet.add(row.getCell(1).toString());
                            plikiZrodloweRegionMap.put(row.getCell(1).toString(), row.getCell(15).toString());
                            plikiZrodloweRodzajMap.put(row.getCell(1).toString(), row.getCell(16).toString());
                            break;
                        }
                    }
                }
            }

//            @@@@@@@@@@@@ this loop gets place name (not path!) where "plik zrodlowy" is.
//            Place name is put to the Map @@@@@@@@@@@@@

            for(Row row : plikiZrodlowe) {
                if (row.getRowNum() > 1) {
                    String cellValue = row.getCell(0).getStringCellValue();
                    for(String plikZrodlowy : plikiZrodloweSet) {
                        if(cellValue.equals(plikZrodlowy)) {
                            plikiZrodloweMiejsceMap.put(row.getCell(0).toString(), row.getCell(3).toString());
                            break;
                        }
                    }
                }
            }


            System.out.println(zasiegiKorektyList);
            System.out.println(plikiZrodloweSet);
            System.out.println(plikiZrodloweRegionMap);
            System.out.println(plikiZrodloweRodzajMap);
            System.out.println(plikiZrodloweMiejsceMap);


            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
