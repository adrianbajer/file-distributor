package spring.rakscode;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.cdrfiles.CdrFile;
import spring.cdrfiles.FileDownloaderImpl;
import spring.excel.ExcelParserImpl;
import spring.excel.ExcelWriterImpl;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RaksCodeController {

    private ExcelParserImpl excelParser;
    private FileDownloaderImpl fileDownloader;
    private ExcelWriterImpl excelWriter;

    public RaksCodeController() {
        excelParser = new ExcelParserImpl();
        fileDownloader = new FileDownloaderImpl();
        excelWriter = new ExcelWriterImpl();
    }

    @RequestMapping("/")
    public String indexGet() {
        return "rakscode/index";
    }

    @RequestMapping(value = "/raksform", method = RequestMethod.GET)
    public ModelAndView showform(Model model) {
        return new ModelAndView("rakscode/raksform","rakscode", new RaksCode());
    }


    @RequestMapping(value = "/givefiles", params="action=view")
    public ModelAndView viewFiles(RaksCode raksCode) {
        return new ModelAndView("rakscode/viewfiles", "cdrFiles", excelParser.getSetOfCdrFiles(raksCode));
    }


    @RequestMapping(value = "/givefiles", params="action=download")
    public ModelAndView downloadFiles(RaksCode raksCode) {

        Set<CdrFile> cdrFileSet = excelParser.getSetOfCdrFiles(raksCode);
        for(CdrFile cdrFile : cdrFileSet) {
            fileDownloader.copyFile(cdrFile);
        }
        excelWriter.saveChangesToExcelFile(cdrFileSet, raksCode);
        return new ModelAndView("redirect:/raksform");
    }


//    @RequestMapping(value = "/givefile")
//    public ModelAndView downloadChosenFile(String cdrFile_name, String cdrFile_place, String cdrFile_region, String cdrFile_type) {
//        CdrFile cdrFile = new CdrFile(cdrFile_name, cdrFile_place, cdrFile_region, cdrFile_type);
//        fileDownloader.copyFile(cdrFile);
//
//        Set<CdrFile> cdrFiles = new HashSet<>();
//        cdrFiles.add(cdrFile);
//
//        return new ModelAndView("rakscode/viewfiles", "cdrFiles", cdrFiles);
//    }
}
