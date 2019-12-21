package excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class ExcelParser {

    public static void main(String[] args) {

        Workbook workbook = null;
        FileInputStream file = null;
        String userInputRaksCode = "Akwitania._map_LAM9_FR";

        List <String> zasiegiKorektyList = new ArrayList<>();
        List <String> plikiZrodloweList = new ArrayList<>();
        Map<String, String> plikiZrodloweRegionMap = new HashMap<>();
        Map<String, String> plikiZrodloweRodzajMap = new HashMap<>();


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


            for(Row row : wydania) {

                // condition skips 3 first rows, which contains unnecessary combined cells and headings
                if (row.getRowNum() > 2) {
                    for (Cell cell : row) {
//                        if (cell.getAddress().toString().startsWith("A") || cell.getAddress().toString().startsWith("L")) {
                        if (cell.getAddress().toString().startsWith("A") && cell.getStringCellValue().equals(userInputRaksCode)) {
                            zasiegiKorektyList.add(row.getCell(11).toString());

//                            switch (cell.getCellType()) {
//                                case NUMERIC:
//                                    System.out.print(cell.getNumericCellValue() + " ");
//                                    break;
//                                case STRING:
//                                    System.out.print(cell.getStringCellValue() + " ");
//                                    break;
//                            }
                        }
                    }
                }
            }
            System.out.println(zasiegiKorektyList);

//         @@@@@@@@@@@@@ loop gets names of source files from "zasiegi_korekty" sheet,
//            basing on "zasiegiKorektyList" values, which are primary key @@@@@@@@@@@@@
            for(Row row : zasiegiKorekty) {
                if (row.getRowNum() > 1) {
                    for (Cell cell : row) {
//                        if (cell.getAddress().toString().startsWith("A") || cell.getAddress().toString().startsWith("L")) {
                        if (cell.getAddress().toString().startsWith("A") && cell.getStringCellValue().equals(userInputRaksCode)) {
                            zasiegiKorektyList.add(row.getCell(11).toString());

//                            switch (cell.getCellType()) {
//                                case NUMERIC:
//                                    System.out.print(cell.getNumericCellValue() + " ");
//                                    break;
//                                case STRING:
//                                    System.out.print(cell.getStringCellValue() + " ");
//                                    break;
//                            }
                        }
                    }
                }
            }





            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        // @@@@@@@@@@@ another way of iterating @@@@@@@@@@@@@@

        //            Iterator<Row> rowIterator = sheet.iterator();
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//
//                Iterator<Cell> cellIterator = row.cellIterator();
//
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//
//                    switch (cell.getCellType()) {
//                        case NUMERIC:
//                            System.out.print(cell.getNumericCellValue() + " ");
//                            break;
//                        case STRING:
//                            System.out.print(cell.getStringCellValue() + " ");
//                            break;
//                    }
//                }
//                System.out.println("");



    }
}
