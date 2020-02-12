package spring.rakscode;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.cdrfiles.CdrFile;
import spring.cdrfiles.FileDownloaderImpl;
import spring.excel.ExcelWriterImpl;
import spring.repository.DataStorageImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Set;

@Controller
public class RaksCodeController {

//    private ExcelParserImpl excelParser;
    private DataStorageImpl dataStorage;
    private FileDownloaderImpl fileDownloader;
    private ExcelWriterImpl excelWriter;

    public RaksCodeController() {
//        excelParser = new ExcelParserImpl();
        dataStorage = new DataStorageImpl();
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


//    @RequestMapping(value = "/givefiles", params="action=view")
//    public ModelAndView viewFiles(RaksCode raksCode) {
//
//        Set<CdrFile> cdrFileSet = dataStorage.getSetOfCdrFiles(raksCode);
//        if (cdrFileSet.size() == 0) {
//            return new ModelAndView("redirect:/message/noproject");
//        }
//        return new ModelAndView("rakscode/viewfiles", "cdrFiles", cdrFileSet);
//    }


//    private static final String FILE_PATH = "src\\main\\resources\\excelfiles\\downloaded_files_data.xls";
    private static final String APPLICATION_XLS = "application/vnd.ms-excel";

    private File createFile() throws IOException {
        File file = new File("excelFileCreated.xls");
        file.createNewFile();
        return file;
    }

//    private File getFile() throws FileNotFoundException {
//        File file = new File(FILE_PATH);
//        if (!file.exists()){
//            throw new FileNotFoundException("file with path: " + FILE_PATH + " was not found.");
//        }
//        return file;
//    }

    @RequestMapping(value = "/givefiles", params="action=download",method = RequestMethod.GET, produces = APPLICATION_XLS)
//    @RequestMapping(value = "/a", method = RequestMethod.GET, produces = APPLICATION_PDF)
    public @ResponseBody void downloadA(HttpServletResponse response) throws IOException {
        File file = createFile();
//        File file = getFile();
        InputStream in = new FileInputStream(new File("excelFileCreated.xls"));

        response.setContentType(APPLICATION_XLS);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }
//    public ModelAndView downloadFiles(RaksCode raksCode) {
//
//        Set<CdrFile> cdrFileSet = dataStorage.getSetOfCdrFiles(raksCode);
//        if (cdrFileSet.size() == 0) {
//            return new ModelAndView("redirect:/message/noproject");
//        }
//
////        @@@@@@ Files which are in other place than archive, can't be downloaded.
////        That's why application denies downloading them. @@@@@@
//        for(CdrFile cdrFile : cdrFileSet) {
//            if (!cdrFile.getPlace().equals("archive")) {
//                return new ModelAndView("redirect:/message/failed");
//            }
//        }
//        excelWriter.saveChangesToExcelFile(cdrFileSet, raksCode);
//        fileDownloader.copyFile(cdrFileSet);
//        return new ModelAndView("redirect:/message/saved");
//    }


//    @RequestMapping(value = "/givefiles", params="action=download")
//    public ModelAndView downloadFiles(RaksCode raksCode) {
//
//        Set<CdrFile> cdrFileSet = dataStorage.getSetOfCdrFiles(raksCode);
//        if (cdrFileSet.size() == 0) {
//            return new ModelAndView("redirect:/message/noproject");
//        }
//
////        @@@@@@ Files which are in other place than archive, can't be downloaded.
////        That's why application denies downloading them. @@@@@@
//        for(CdrFile cdrFile : cdrFileSet) {
//            if (!cdrFile.getPlace().equals("archive")) {
//            return new ModelAndView("redirect:/message/failed");
//            }
//        }
//        excelWriter.saveChangesToExcelFile(cdrFileSet, raksCode);
//        fileDownloader.copyFile(cdrFileSet);
//        return new ModelAndView("redirect:/message/saved");
//    }



    @RequestMapping("/message/failed")
    public String messageFailed() {
        return "rakscode/messagefailed";
    }

    @RequestMapping("/message/ok")
    public String messageOk() {
        return "rakscode/messageok";
    }

    @RequestMapping("/message/saved")
    public String messageSaved() {
        return "rakscode/messagesaved";
    }

    @RequestMapping("/message/noproject")
    public String messageNoProject() {
        return "rakscode/messagenoproject";
    }

//    Set<CdrFile> cdrFileSet = excelParser.getSetOfCdrFiles(raksCode);
//        for(CdrFile cdrFile : cdrFileSet) {
//            if (raksCode.getJobType()==JobType.PUBLICATION){
//                fileDownloader.copyFile(cdrFile);
//                return new ModelAndView("redirect:/raksform");
//            }
//            else {
//                if (!cdrFile.getPlace().equals("archive")){
//                    return new ModelAndView("redirect:/message");
//                }
//            }
//        }
//        excelWriter.saveChangesToExcelFile(cdrFileSet, raksCode);
//        return new ModelAndView("redirect:/raksform");

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
