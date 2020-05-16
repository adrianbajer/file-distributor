package spring.cdrfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDownloaderImpl implements FileDownloader {

    @Override
    public void copyFile(CdrFile cdrFile) {

        PathCreator pathCreator = new PathCreatorImpl();

        Path srcFile = Paths.get(pathCreator.createPath(cdrFile));
        Path dstFile = Paths.get("C:\\fd\\result copies\\" + cdrFile.getName() + ".txt");

        try {
            Files.copy(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public List<String> getListOfFilesAndDirectories(String mainDirPath) {
        return Stream.of(new File(mainDirPath).listFiles())
                .map(File::getName)
                .collect(Collectors.toList());
    }


    @Override
    public String findLatestVDirectory(List<String> listOfAllFilesAndDirectories) {
        Pattern pattern = Pattern.compile("v[0-9][0-9]-");

        List<String> listOfvDirectories= listOfAllFilesAndDirectories.stream()
                .filter(pattern.asPredicate())
                .collect(Collectors.toList());
        Collections.sort(listOfvDirectories);

        return listOfvDirectories.get(listOfvDirectories.size() - 1);
    }
}
