package spring.rakscode;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import spring.cdrfiles.CdrFile;
import spring.excel.ExcelParserImpl;
import spring.excel.ExcelWriterImpl;
import spring.maps.Polygon;
import spring.maps.PolygonText;
import spring.service.CdrFilesServiceImpl;
import spring.service.PolygonServiceImpl;
import spring.service.RaksCodeServiceImpl;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class RaksCodeController {
    private List<RaksCode> raksCodeList;
    private List<Polygon> polygonList;
    private CdrFilesServiceImpl cdrFilesServiceImpl;
    private RaksCodeServiceImpl raksCodeServiceImpl;
    private PolygonServiceImpl polygonServiceImpl;
    private ExcelWriterImpl excelWriterImpl;
    private ExcelParserImpl excelParserImpl;

    public RaksCodeController(CdrFilesServiceImpl cdrFilesServiceImpl, RaksCodeServiceImpl raksCodeServiceImpl, PolygonServiceImpl polygonServiceImpl) {
        this.cdrFilesServiceImpl = cdrFilesServiceImpl;
        this.raksCodeServiceImpl = raksCodeServiceImpl;
        this.polygonServiceImpl = polygonServiceImpl;
        raksCodeList = raksCodeServiceImpl.getAll();
        polygonList = polygonServiceImpl.getAll();
        excelWriterImpl = new ExcelWriterImpl();
        excelParserImpl = new ExcelParserImpl();
    }

//    -------------------------------------------------------------------

//    @RequestMapping("/")
//    public String indexGet() {
//        return "rakscode/index";
//    }

    @RequestMapping("/")
    public String showForm(RaksCode raksCode, Model model) {

        if (raksCode.getId() != 0){

            Polygon polygonFromDatabase = getPolygonById(raksCode.getPolygonId());
            Polygon polygon = new Polygon();
            polygon.setLeftUpperLat(polygonFromDatabase.getLeftUpperLat());
            polygon.setLeftUpperLon(polygonFromDatabase.getLeftUpperLon());
            polygon.setRightLowerLat(polygonFromDatabase.getRightLowerLat());
            polygon.setRightLowerLon(polygonFromDatabase.getRightLowerLon());

            model.addAttribute("polygon", polygon);
        }

        model.addAttribute("rakscode", raksCode);
        model.addAttribute("raksCodeList", raksCodeList);

        return "rakscode/mapranges";
    }


//    @RequestMapping(value = "/raksform", method = RequestMethod.GET)
//    public String showForm(Model model) {
//        raksCodeList = raksCodeServiceImpl.getAll();
//        model.addAttribute("rakscode", new RaksCode());
//        model.addAttribute("raksCodeList", raksCodeList);
//        return "rakscode/raksform";
//    }


//    -------------------------------------------------------------------
//    @@@@ mappings for viewing files in table form and on a map @@@@
//    -------------------------------------------------------------------

    @RequestMapping(value = "/givefiles", params="action=view")
    public ModelAndView viewFiles(RaksCode raksCode) {
        Set<CdrFile> cdrFileSet = raksCode.getCdrFileSet();
        if (cdrFileSet.size() == 0) {
            return new ModelAndView("redirect:/message/noproject");
        }
        return new ModelAndView("rakscode/viewfiles", "cdrFiles", cdrFileSet);
    }


    @RequestMapping(value = "/givefiles", params="action=view_on_map")
    public ModelAndView viewRangesOnMap(RaksCode raksCode, Model model) {
        Set<CdrFile> cdrFileSet = raksCode.getCdrFileSet();
        if (cdrFileSet.size() == 0) {
            return new ModelAndView("redirect:/message/noproject");
        }

        RaksCode raksCodeFromDatabase = getRaksCodeById(raksCode.getId());
        Polygon polygonFromDatabase = getPolygonById(raksCodeFromDatabase.getPolygonId());

        StringBuilder firstLineForPolygonPopup = new StringBuilder();
        firstLineForPolygonPopup.append("RAKS code name: ");
        firstLineForPolygonPopup.append("[");
        firstLineForPolygonPopup.append(raksCodeFromDatabase.getRaksCode());
        firstLineForPolygonPopup.append("] ");

        StringBuilder secondLineForPolygonPopup = new StringBuilder();
        secondLineForPolygonPopup.append("Component files: ");
        for(CdrFile cdrFile : cdrFileSet) {
            secondLineForPolygonPopup.append("[");
            secondLineForPolygonPopup.append(cdrFile.getName());
            secondLineForPolygonPopup.append("] ");
        }

        Polygon polygon = new Polygon();
        polygon.setLeftUpperLat(polygonFromDatabase.getLeftUpperLat());
        polygon.setLeftUpperLon(polygonFromDatabase.getLeftUpperLon());
        polygon.setRightLowerLat(polygonFromDatabase.getRightLowerLat());
        polygon.setRightLowerLon(polygonFromDatabase.getRightLowerLon());

        PolygonText text = new PolygonText();
        text.setRaksCodeName(firstLineForPolygonPopup.toString());
        text.setComponentFilesNames(secondLineForPolygonPopup.toString());

        model.addAttribute("polygon", polygon);
        model.addAttribute("text", text);

        return new ModelAndView("rakscode/mapranges");
    }


//    -------------------------------------------------------------------
//    @@@@ mappings for creating excel file and downloading it @@@@
//    -------------------------------------------------------------------

    private static final String APPLICATION_XLS = "application/vnd.ms-excel";

    @RequestMapping(value = "/givefiles", params="action=download",method = RequestMethod.GET, produces = APPLICATION_XLS)
    public @ResponseBody void downloadA(HttpServletResponse response, RaksCode raksCode) throws IOException {
        Set<CdrFile> cdrFileSet = raksCode.getCdrFileSet();

        RaksCode raksCodeToUpdate = getRaksCodeById(raksCode.getId());
        updateRaksCodeInDatabase(raksCodeToUpdate);

//        //raksCode processed by ThymeLeaf has name = id, so to update database properly,
//////        an original raksCode is taken from database, its two lacking attributes are added (taken from raksCode sent by ThymeLeaf)
//////        and then raksCodeToUpdate is send back to database
////
////        RaksCode raksCodeToUpdate = getRaksCodeById(raksCode.getId());
////        raksCodeToUpdate.setUserName(raksCode.getUserName());
////        raksCodeToUpdate.setJobType(raksCode.getJobType());
////        updateRaksCodeInDatabase(raksCodeToUpdate);
////
//////        next part of code updates "place" field of each cdrFile in cdrFileSet,
//////        according to raksCode UserName value
////
////        for(CdrFile cdrFile : cdrFileSet){
////            cdrFile.setPlace(raksCode.getUserName().getName());
////            updateCdrFileInDatabase(cdrFile);
////        }


//        here an excel file is created and filled with values

        File file = excelWriterImpl.createAndFillExcelFile(cdrFileSet, raksCode);
        InputStream in = new FileInputStream(file);

        response.setContentType(APPLICATION_XLS);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }


//    -------------------------------------------------------------------
//    @@@@ mappings for uploading excel file and updating database @@@@
//    -------------------------------------------------------------------

    @RequestMapping(value = "/givefiles", params="action=upload")
    public String showUploadForm() {
        return "rakscode/uploading";
    }

    @RequestMapping(value = "/uploading")
    public String showUploadFormFromMessageDialog() {
        return "rakscode/uploading";
    }

    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam MultipartFile file, HttpSession session){
        String fullFilename = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(fullFilename);
        String filenameWithoutExtension;

        if (fullFilename.length() > 0){
            filenameWithoutExtension = fullFilename.substring(0, 12);
        } else {
            return new ModelAndView("redirect:/message/nofile");
        }

        RaksCode raksCodeFromUploadedFile = getRaksCodeByName(filenameWithoutExtension);
        Set<CdrFile> cdrFileSet;

        if (raksCodeFromUploadedFile != null && fileExtension.equals("xls")) {
            cdrFileSet = excelParserImpl.getSetOfCdrFilesFromUploadedFile(file, raksCodeFromUploadedFile);
        } else {
            return new ModelAndView("redirect:/message/notvalidfile");
        }

        for(CdrFile cdrFile : cdrFileSet){
            updateCdrFileInDatabase(cdrFile);
        }

        Set<CdrFile> cdrFileSetFromDatabase = raksCodeFromUploadedFile.getCdrFileSet();

        return new ModelAndView("rakscode/viewfiles", "cdrFiles", cdrFileSetFromDatabase);
    }

//    -------------------------------------------------------------------

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

    @RequestMapping("/message/nofile")
    public String messageNoFile() {
        return "rakscode/messagenofile";
    }

    @RequestMapping("/message/notvalidfile")
    public String messageNotValidFile() {
        return "rakscode/messagenotvalidfile";
    }

//    -------------------------------------------------------------------

    private RaksCode getRaksCodeById(@RequestParam int id) {
        return raksCodeList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private Polygon getPolygonById(@RequestParam int id) {
        return polygonList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private RaksCode getRaksCodeByName(@RequestParam String name) {
        try {
            return raksCodeList.stream().filter(f -> f.getRaksCode().equals(name)).findFirst().get();
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void updateRaksCodeInDatabase(RaksCode raksCode) {
        try {
            raksCodeServiceImpl.update(raksCode);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void updateCdrFileInDatabase(CdrFile cdrFile) {
        try {
            cdrFilesServiceImpl.update(cdrFile);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


}