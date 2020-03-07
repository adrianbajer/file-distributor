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
import spring.rakscode.RaksCode;
import spring.service.CdrFilesServiceImpl;
import spring.service.RaksCodeServiceImpl;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLOutput;
import java.util.List;

@Controller
public class ExcelUploadController {
    private List<RaksCode> raksCodeList;
    private CdrFilesServiceImpl cdrFilesServiceImpl;
    private RaksCodeServiceImpl raksCodeServiceImpl;
    private ExcelWriterImpl excelWriterImpl;

    public ExcelUploadController(CdrFilesServiceImpl cdrFilesServiceImpl, RaksCodeServiceImpl raksCodeServiceImpl) {
        this.cdrFilesServiceImpl = cdrFilesServiceImpl;
        this.raksCodeServiceImpl = raksCodeServiceImpl;
        raksCodeList = raksCodeServiceImpl.getAll();
        excelWriterImpl = new ExcelWriterImpl();
    }

    @RequestMapping("/uploadform")
    public String showUploadForm() {
        return "rakscode/uploading";
    }

    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam MultipartFile file, HttpSession session){
        String path=session.getServletContext().getRealPath("/");
        String fullFilename = file.getOriginalFilename();
        String filenameWithoutExtension = fullFilename.substring(0, fullFilename.length() - 4);

        System.out.println(filenameWithoutExtension);

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet cdrFilesDataToUpdate = workbook.getSheetAt(0);
            String cellValueFromUploadedFile = cdrFilesDataToUpdate.getRow(2).getCell(0).getStringCellValue();

            System.out.println(cellValueFromUploadedFile);

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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

        return new ModelAndView("rakscode/uploadedfilename","filename",filenameWithoutExtension);
    }

    private RaksCode getRaksCodeByName(@RequestParam String name) {
        return raksCodeList.stream().filter(f -> f.getRaksCode() == name).findFirst().get();
    }

}
