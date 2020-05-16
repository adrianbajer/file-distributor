package spring.cdrfiles;

import java.util.List;
import java.util.stream.Stream;

public interface FileDownloader {

    void copyFile(CdrFile cdrFile);

    List<String> getListOfFilesAndDirectories(String mainDirPath);

    String findLatestVDirectory(List<String> listOfvDirectories);

}
