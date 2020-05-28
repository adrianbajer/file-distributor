package spring.cdrfiles;

import java.util.List;
import java.util.stream.Stream;

public interface FileDownloader {

    void copyFile(CdrFile cdrFile);

    void copyFile(String pathToFile, String mainDirPath);

    List<String> getListOfPathsToFilesAndDirs(String mainDirPath);

    List<String> getListOfPathsToFilesOrDirs(String mainDirPath, int fileOrDir);

    String findPathToLatestVDirectory(List<String> listOfAllFilesAndDirectories);

    List<String> findPathsMatchingRegex(List<String> listOfAllFilesAndDirectories, String regex);

}
