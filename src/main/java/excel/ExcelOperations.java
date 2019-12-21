package excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelOperations {

    public static void main(String[] args) {
        insertValuesInCells();
    }


    public static void insertValuesInCells(){
        Workbook workbook = null;
        FileInputStream file = null;
        List<String> valuesToInputList = new ArrayList<>();
        valuesToInputList.add("2 mapa samochodowa");
        valuesToInputList.add("2 mapa samochodowa");
        valuesToInputList.add("1 plan miasta");
        valuesToInputList.add("1 plan miasta");
        valuesToInputList.add("2 mapa samochodowa");

        try {
            file = new FileInputStream(new File("C:\\Users\\Adrian\\Documents\\Nauka programowania\\" +
                    "FileDistributor_materiały\\pliki xls\\Baza archiwum FD.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            workbook = new HSSFWorkbook(file);
            Sheet wydania = workbook.getSheetAt(0);
            Sheet plikiZrodlowe = workbook.getSheetAt(1);
            Sheet zasiegiKorekty = workbook.getSheetAt(2);

            for(int i = 2; i < 7; i++) {
                Row row = zasiegiKorekty.getRow(i);
                Cell cellToInput = row.getCell(16);
//                System.out.println(updateName);
//                System.out.println(valuesToInputList.get(i - 2));
                cellToInput.setCellValue(valuesToInputList.get(i - 2));
            }

            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


//        workbook.createSheet("mojArkusz");
//        System.out.println(workbook.getSheetAt(0).getSheetName());
//        System.out.println(workbook.getSheetAt(1).getSheetName());


        try {
            FileOutputStream out = new FileOutputStream("C:\\Users\\Adrian\\Documents\\" +
                    "Nauka programowania\\FileDistributor_materiały\\pliki xls\\Baza archiwum FD2.xls");

            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
