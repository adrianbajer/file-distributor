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
import spring.service.CdrFilesServiceImpl;
import spring.service.RaksCodeServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Set;

@Controller
public class RaksCodeController {
    private List<RaksCode> raksCodeList;
    private CdrFilesServiceImpl cdrFilesServiceImpl;
    private RaksCodeServiceImpl raksCodeServiceImpl;

//    private DataStorageImpl dataStorageImpl;
//    private FileDownloaderImpl fileDownloader;
    private ExcelWriterImpl excelWriterImpl;

    public RaksCodeController(CdrFilesServiceImpl cdrFilesServiceImpl, RaksCodeServiceImpl raksCodeServiceImpl) {
        this.cdrFilesServiceImpl = cdrFilesServiceImpl;
        this.raksCodeServiceImpl = raksCodeServiceImpl;
        raksCodeList = raksCodeServiceImpl.getAll();

//        dataStorageImpl = new DataStorageImpl();
//        fileDownloader = new FileDownloaderImpl();
        excelWriterImpl = new ExcelWriterImpl();
    }

    @RequestMapping("/")
    public String indexGet() {
        return "rakscode/index";
    }

    @RequestMapping(value = "/raksform", method = RequestMethod.GET)
    public String showForm(Model model) {
        raksCodeList = raksCodeServiceImpl.getAll();
        model.addAttribute("rakscode", new RaksCode());
        model.addAttribute("raksCodeList", raksCodeList);
        return "rakscode/raksform";
    }


    @RequestMapping(value = "/givefiles", params="action=view")
    public ModelAndView viewFiles(RaksCode raksCode) {
        Set<CdrFile> cdrFileSet = raksCode.getCdrFileSet();
        if (cdrFileSet.size() == 0) {
            return new ModelAndView("redirect:/message/noproject");
        }
        return new ModelAndView("rakscode/viewfiles", "cdrFiles", cdrFileSet);
    }


//    method and mapping for creating excel file and downloading it

    private static final String APPLICATION_XLS = "application/vnd.ms-excel";

    @RequestMapping(value = "/givefiles", params="action=download",method = RequestMethod.GET, produces = APPLICATION_XLS)
    public @ResponseBody void downloadA(HttpServletResponse response, RaksCode raksCode) throws IOException {
        Set<CdrFile> cdrFileSet = raksCode.getCdrFileSet();

        RaksCode raksCodeToUpdate = getRaksCodeById(raksCode.getId());
        raksCodeToUpdate.setUserName(raksCode.getUserName());
        raksCodeToUpdate.setJobType(raksCode.getJobType());
        updateRaksCodeInDatabase(raksCodeToUpdate);

        File file = excelWriterImpl.createAndFillExcelFile(cdrFileSet, raksCode);
        InputStream in = new FileInputStream(file);

        response.setContentType(APPLICATION_XLS);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }

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



    private RaksCode getRaksCodeById(@RequestParam int id) {
        return raksCodeList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private void updateRaksCodeInDatabase(RaksCode raksCode) {
        try {
            raksCodeServiceImpl.update(raksCode);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


}