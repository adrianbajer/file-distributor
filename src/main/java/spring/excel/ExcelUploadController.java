package spring.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLOutput;

@Controller
public class ExcelUploadController {

    @RequestMapping("/uploadform")
    public String showUploadForm() {
        return "rakscode/uploading";
    }

    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam MultipartFile file, HttpSession session){
        String path=session.getServletContext().getRealPath("/");
        String filename=file.getOriginalFilename();

        System.out.println(path+" "+filename);
        try{
            byte barr[]=file.getBytes();

            BufferedOutputStream bout=new BufferedOutputStream(
                    new FileOutputStream(path+"/"+filename));
            bout.write(barr);
            bout.flush();
            bout.close();

        }catch(Exception e){System.out.println(e);}
        return new ModelAndView("rakscode/uploadedfilename","filename",path+"/"+filename);
    }


//    @RequestMapping(value="/upload",method= RequestMethod.POST)
//    public String handleFileUpload(@RequestParam MultipartFile file, HttpSession session, Model model){
//        String path=session.getServletContext().getRealPath("/");
//        String filename=file.getOriginalFilename();
//        model.addAttribute("fileName", filename);
//
//        System.out.println(path+" "+filename);
//        try{
//            byte barr[]=file.getBytes();
//
//            BufferedOutputStream bout=new BufferedOutputStream(
//                    new FileOutputStream(path+"/"+filename));
//            bout.write(barr);
//            bout.flush();
//            bout.close();
//
//        }catch(Exception e){System.out.println(e);}
//        return "rakscode/uploadedfilename";
//    }


//    @RequestMapping("/upload")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
////    public String handleFileUpload() {
//
//        System.out.println("I'm Uploading Controller");
////        try {
////            InputStream inputStream = file.getInputStream();
////            Workbook workbook = new HSSFWorkbook(inputStream);
////            Sheet cdrFilesDataToUpdate = workbook.getSheetAt(0);
////            String cellValueFromUploadedFile = cdrFilesDataToUpdate.getRow(1).getCell(0).getStringCellValue();
////
////            System.out.println(cellValueFromUploadedFile);
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//
////        storageService.store(file);
////        redirectAttributes.addFlashAttribute("message",
////                "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "rakscode/uploading";
//    }
}
