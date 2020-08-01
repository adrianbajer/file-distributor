package spring.rakscode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.cdrfiles.CdrFile;
import spring.cdrfiles.FileDownloaderImpl;
import spring.excel.ExcelParserImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CdrFileController {

    private ExcelParserImpl excelParser;
    private FileDownloaderImpl fileDownloader;
    private List<CdrFile> cdrFileList;

    public CdrFileController(ExcelParserImpl excelParser, FileDownloaderImpl fileDownloader) {
        this.excelParser = excelParser;
        this.fileDownloader = fileDownloader;
        cdrFileList = excelParser.getListOfCdrFiles();
    }

    @RequestMapping("/cdrform")
    public String showFormWhenStarting(Model model) {

        model.addAttribute("cdrFile", new CdrFile());
        model.addAttribute("cdrFileList", cdrFileList);

        return "rakscode/cdrform";
    }

    @RequestMapping(value ="/cdrform", params="action=showdetails")
    public String showDetailsOfSelectedFile(CdrFile cdrFile, Model model) {

        Set<CdrFile> cdrFileWithDetailsSet = new HashSet<>();

        for (CdrFile cdrFileFromList : cdrFileList) {
            if (cdrFileFromList.getName().equals(cdrFile.getName())){
                cdrFileWithDetailsSet.add(cdrFileFromList);
                break;
            }
        }

        model.addAttribute("cdrFile", new CdrFile());
        model.addAttribute("cdrFileWithDetailsSet", cdrFileWithDetailsSet);
        model.addAttribute("cdrFileList", cdrFileList);

        return "rakscode/cdrform";
    }
}
