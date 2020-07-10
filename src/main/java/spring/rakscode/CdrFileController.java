package spring.rakscode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.cdrfiles.CdrFile;
import spring.cdrfiles.FileDownloaderImpl;
import spring.excel.ExcelParserImpl;

import java.util.List;

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
}
