package spring.rakscode;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.cdrfiles.CdrFile;
import spring.cdrfiles.FileDownloaderImpl;
import spring.cdrfiles.PathCreatorImpl;
import spring.excel.ExcelParserImpl;
import spring.excel.ExcelWriterImpl;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class RaksCodeController {

    private ExcelParserImpl excelParser;
    private FileDownloaderImpl fileDownloader;
    private ExcelWriterImpl excelWriter;
    private List<RaksCode> raksCodeList;

    public RaksCodeController() {
        excelParser = new ExcelParserImpl();
        fileDownloader = new FileDownloaderImpl();
        excelWriter = new ExcelWriterImpl();
        raksCodeList = excelParser.getListOfRaksCodes();
    }

    @RequestMapping("/")
    public String showFormWhenStarting(Model model) {

        model.addAttribute("raksCode", new RaksCode());
        model.addAttribute("raksCodeList", raksCodeList);

        return "rakscode/raksform";
    }

    @RequestMapping(value = "/raksform", method = RequestMethod.GET)
    public String showComponentFiles(RaksCode raksCode, Model model) {

        Set<CdrFile> cdrFileSet = excelParser.getSetOfCdrFiles(raksCode);
        if (cdrFileSet.size() == 0) {
            return "rakscode/messagenoproject";
        }

        model.addAttribute("cdrFiles", cdrFileSet);
        model.addAttribute("raksCode", new RaksCode());
        model.addAttribute("raksCodeList", raksCodeList);

        return "rakscode/raksform";
    }


    @RequestMapping(value = "/raksform", params="action=publication")
    public String copyFilesForPublication(RaksCode raksCode, Model model) {

        Set<CdrFile> cdrFileSet = excelParser.getSetOfCdrFiles(raksCode);
        if (cdrFileSet.size() == 0) {
            return "rakscode/messagenoproject";
        }

        model.addAttribute("cdrFiles", cdrFileSet);
        model.addAttribute("raksCode", new RaksCode());
        model.addAttribute("raksCodeList", raksCodeList);

        return "rakscode/raksform";
    }


    @RequestMapping(value = "/raksform", params="action=updating")
    public String copyFilesForUpdating(RaksCode raksCode, Model model) {

        String latestVDirPath = "";
        Set<CdrFile> cdrFileSet = excelParser.getSetOfCdrFiles(raksCode);
        if (cdrFileSet.size() == 0) {
            return "rakscode/messagenoproject";
        }

        //     @@@@@@@@@@@@@@@   do testowania    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        PathCreatorImpl pathCreator = new PathCreatorImpl();
        List<String> listOfPathsToFilesAndDirectoriesInMainDir = new ArrayList<>();
        List<String> listOfPathsToFilesInLatestVDir = new ArrayList<>();
        List<String> listOfPathsToDirsInLatestVDir = new ArrayList<>();
        List<String> listOfPathsToFilesAndDirsInLatestVDir = new ArrayList<>();
        List<String> listOfPathsToXyz = new ArrayList<>();


        for(CdrFile cdrFile : cdrFileSet){
//            we are in main dir now

            listOfPathsToFilesAndDirectoriesInMainDir = fileDownloader.getListOfPathsToFilesAndDirs(pathCreator.createPath(cdrFile));

//            we are copying "XYZ_korekta_do_wprowadzenia" dir
            listOfPathsToXyz = fileDownloader.findPathsMatchingRegex(listOfPathsToFilesAndDirectoriesInMainDir,"XYZ");

            for (String pathToDir : listOfPathsToXyz) {
                fileDownloader.copyDir(pathToDir);
            }


            latestVDirPath = fileDownloader.findPathToLatestVDirectory(listOfPathsToFilesAndDirectoriesInMainDir);
            listOfPathsToFilesInLatestVDir = fileDownloader.getListOfPathsToFilesOrDirs(latestVDirPath, 1);
//            System.out.println(listOfPathsToFilesAndDirectoriesInMainDir);
//            System.out.println(latestVDirPath);
//            System.out.println(listOfPathsToFilesInLatestVDir);

            for(String pathToFile : listOfPathsToFilesInLatestVDir) {
//                we are in latestVDir now and copy all files
                fileDownloader.copyFile(pathToFile, latestVDirPath);
            }

        }

//        listOfPathsToXyzAndKorektaXls.forEach(System.out::println);


//      1- files, 2 -dirs, 3 -files and dirs
//        listOfPathsToFilesInLatestVDir = fileDownloader.getListOfPathsToFilesOrDirs(latestVDirPath, 1);
//        listOfPathsToDirsInLatestVDir = fileDownloader.getListOfPathsToFilesOrDirs(latestVDirPath, 2);
//        listOfPathsToFilesAndDirsInLatestVDir = fileDownloader.getListOfPathsToFilesOrDirs(latestVDirPath, 3);

//        listOfPathsToFilesInLatestVDir.forEach(System.out::println);
//        System.out.println("___________________________________");
//        listOfPathsToDirsInLatestVDir.forEach(System.out::println);
//        System.out.println("___________________________________");
//        listOfPathsToFilesAndDirsInLatestVDir.forEach(System.out::println);
//        System.out.println("___________________________________");

//        for(String pathToFile : listOfPathsToFilesAndDirectoriesInMainDir) {
//            fileDownloader.copyFile(pathToFile, latestVDirPath);
//        }
//




//     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


        model.addAttribute("cdrFiles", cdrFileSet);
        model.addAttribute("raksCode", new RaksCode());
        model.addAttribute("raksCodeList", raksCodeList);

        return "rakscode/raksform";
    }


//    @RequestMapping(value = "/givefiles", params="action=view")
//    public ModelAndView viewFiles(RaksCode raksCode) {
//
//        Set<CdrFile> cdrFileSet = excelParser.getSetOfCdrFiles(raksCode);
//        if (cdrFileSet.size() == 0) {
//            return new ModelAndView("redirect:/message/noproject");
//        }
//        return new ModelAndView("rakscode/viewfiles", "cdrFiles", cdrFileSet);
//    }

//
//    @RequestMapping(value = "/givefiles", params="action=download")
//    public ModelAndView downloadFiles(RaksCode raksCode) {
//
//        Set<CdrFile> cdrFileSet = excelParser.getSetOfCdrFiles(raksCode);
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
//
////        @@@@@@ JobType.PUBLICATION means that files won't be modified.
////        That's why application downloads files and doesn't make any changes in excel database. @@@@@@
//        if (raksCode.getJobType()==JobType.PUBLICATION){
//            for(CdrFile cdrFile : cdrFileSet) {
//                fileDownloader.copyFile(cdrFile);
//            }
//            return new ModelAndView("redirect:/message/ok");
//        } else {
//            for(CdrFile cdrFile : cdrFileSet) {
//                fileDownloader.copyFile(cdrFile);
//            }
//        }
//        excelWriter.saveChangesToExcelFile(cdrFileSet, raksCode);
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

}
