package spring.rakscode;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.cdrfiles.CdrFile;
import spring.cdrfiles.FileDownloaderImpl;
import spring.cdrfiles.PathCreatorImpl;
import spring.excel.ExcelParserImpl;
import spring.excel.ExcelWriterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class RaksCodeController {

    private ExcelParserImpl excelParser;
    private FileDownloaderImpl fileDownloader;
    private ExcelWriterImpl excelWriter;
    private List<RaksCode> raksCodeList;

    public RaksCodeController(ExcelParserImpl excelParser, FileDownloaderImpl fileDownloader) {
        this.excelParser = excelParser;
        this.fileDownloader = fileDownloader;
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

//        for (RaksCode element : raksCodeList) {
//            if (element.getRaksCode().equals(raksCode.getRaksCode())) {
//
//            }
//        }
//        System.out.println(raksCodeList.indexOf(raksCode.getRaksCode()));

        raksCodeList.add(0, raksCode);

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

        raksCodeList.add(0, raksCode);

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

        raksCodeList.add(0, raksCode);

        //     @@@@@@@@@@@@@@@   do testowania    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        PathCreatorImpl pathCreator = new PathCreatorImpl();
        List<String> listOfPathsToFilesAndDirectoriesInMainDir = new ArrayList<>();
        List<String> listOfPathsToFilesInLatestVDir = new ArrayList<>();
        List<String> listOfPathsToDirsInLatestVDir = new ArrayList<>();
        List<String> listWithPathsToLegendsMetroAndIndexes= new ArrayList<>();
        List<String> listWithPathToXyz = new ArrayList<>();
        List<String> listWithPathToKorektaXls = new ArrayList<>();


        for(CdrFile cdrFile : cdrFileSet){
//            we are in main dir now

//            for network use
            try {
                listOfPathsToFilesAndDirectoriesInMainDir = fileDownloader.getListOfPathsToFilesAndDirs(pathCreator.createPathForLocalUse(cdrFile));


//            for local use
//            listOfPathsToFilesAndDirectoriesInMainDir = fileDownloader.getListOfPathsToFilesAndDirs(cdrFile.getPath());

//            we are copying "XYZ_korekta_do_wprowadzenia" dir
            listWithPathToXyz = fileDownloader.findPathsMatchingRegex(listOfPathsToFilesAndDirectoriesInMainDir,"XYZ");

            for (String pathToDir : listWithPathToXyz) {
                fileDownloader.copyDirFromMainDir(pathToDir);
            }

//            we are copying "korekta.xls" file
            listWithPathToKorektaXls = fileDownloader.findPathsMatchingRegex(listOfPathsToFilesAndDirectoriesInMainDir,"korekta.xls");

            for (String pathToFile : listWithPathToKorektaXls) {
                fileDownloader.copyFileFromMainDir(pathToFile);
            }

            latestVDirPath = fileDownloader.findPathToLatestVDirectory(listOfPathsToFilesAndDirectoriesInMainDir);
            listOfPathsToFilesInLatestVDir = fileDownloader.getListOfPathsToFilesOrDirs(latestVDirPath, 1);
            listOfPathsToDirsInLatestVDir = fileDownloader.getListOfPathsToFilesOrDirs(latestVDirPath, 2);
//            System.out.println(listOfPathsToFilesAndDirectoriesInMainDir);
//            System.out.println(latestVDirPath);
//            System.out.println(listOfPathsToFilesInLatestVDir);

//            we are in latestVDir now and copy all files

            for(String pathToFile : listOfPathsToFilesInLatestVDir) {
                fileDownloader.copyFileFromLatestVDir(pathToFile);
            }

//            we are in latestVDir now and copy dirs matching regex
            listWithPathsToLegendsMetroAndIndexes = fileDownloader.findPathsMatchingRegex(listOfPathsToDirsInLatestVDir, "legend|metro|indeks");
            for(String pathToDir : listWithPathsToLegendsMetroAndIndexes) {
                fileDownloader.copyDirFromLatestVDir(pathToDir);
            }

            } catch (NullPointerException e) {
                e.printStackTrace();
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
