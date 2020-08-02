package spring.rakscode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.cdrfiles.CdrFile;
import spring.cdrfiles.FileDownloaderImpl;
import spring.cdrfiles.PathCreatorImpl;
import spring.excel.ExcelParserImpl;

import java.util.ArrayList;
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


    @RequestMapping(value ="/cdrform", params="action=copy")
    public String copySelectedFile(CdrFile cdrFile, Model model) {

        Set<CdrFile> cdrFileWithDetailsSet = new HashSet<>();

        for (CdrFile cdrFileFromList : cdrFileList) {
            if (cdrFileFromList.getName().equals(cdrFile.getName())){
                cdrFileWithDetailsSet.add(cdrFileFromList);
                break;
            }
        }

        PathCreatorImpl pathCreator = new PathCreatorImpl();
        String latestVDirPath = "";
        List<String> listOfPathsToFilesAndDirectoriesInMainDir;
        List<String> listOfPathsToFilesInLatestVDir = new ArrayList<>();
        List<String> listOfPathsToDirsInLatestVDir = new ArrayList<>();
        List<String> listWithPathsToLegendsMetroAndIndexes= new ArrayList<>();
        List<String> listWithPathToXyz = new ArrayList<>();
        List<String> listWithPathToKorektaXls = new ArrayList<>();


        for(CdrFile selectedCdrFile : cdrFileWithDetailsSet){
//            we are in main dir now

//            for network use
            try {
                listOfPathsToFilesAndDirectoriesInMainDir = fileDownloader.getListOfPathsToFilesAndDirs(pathCreator.createPathForLocalUse(selectedCdrFile));
//
//
////            for local use
////            listOfPathsToFilesAndDirectoriesInMainDir = fileDownloader.getListOfPathsToFilesAndDirs(cdrFile.getPath());
//
////            we are copying "XYZ_korekta_do_wprowadzenia" dir
//                listWithPathToXyz = fileDownloader.findPathsMatchingRegex(listOfPathsToFilesAndDirectoriesInMainDir,"XYZ");
//
//                for (String pathToDir : listWithPathToXyz) {
//                    fileDownloader.copyDirFromMainDir(pathToDir);
//                }
//
////            we are copying "korekta.xls" file
//                listWithPathToKorektaXls = fileDownloader.findPathsMatchingRegex(listOfPathsToFilesAndDirectoriesInMainDir,"korekta.xls");
//
//                for (String pathToFile : listWithPathToKorektaXls) {
//                    fileDownloader.copyFileFromMainDir(pathToFile);
//                }
//
                latestVDirPath = fileDownloader.findPathToLatestVDirectory(listOfPathsToFilesAndDirectoriesInMainDir);
                listOfPathsToFilesInLatestVDir = fileDownloader.getListOfPathsToFilesOrDirs(latestVDirPath, 1);
//                listOfPathsToDirsInLatestVDir = fileDownloader.getListOfPathsToFilesOrDirs(latestVDirPath, 2);
////            System.out.println(listOfPathsToFilesAndDirectoriesInMainDir);
////            System.out.println(latestVDirPath);
////            System.out.println(listOfPathsToFilesInLatestVDir);
//
//            we are in latestVDir now and copy selected file (assumed that only selected file name begins with selectedCdrFile.getName()

                for(String pathToFile : listOfPathsToFilesInLatestVDir) {
                    if(pathToFile.substring(pathToFile.lastIndexOf("\\") + 1).startsWith(selectedCdrFile.getName()))
                    fileDownloader.copyFileFromLatestVDir(pathToFile);
                }
//
////            we are in latestVDir now and copy dirs matching regex
//                listWithPathsToLegendsMetroAndIndexes = fileDownloader.findPathsMatchingRegex(listOfPathsToDirsInLatestVDir, "legend|metro|indeks");
//                for(String pathToDir : listWithPathsToLegendsMetroAndIndexes) {
//                    fileDownloader.copyDirFromLatestVDir(pathToDir);
//                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("cdrFile", new CdrFile());
        model.addAttribute("cdrFileWithDetailsSet", cdrFileWithDetailsSet);
        model.addAttribute("cdrFileList", cdrFileList);

        return "rakscode/cdrform";
    }
}
