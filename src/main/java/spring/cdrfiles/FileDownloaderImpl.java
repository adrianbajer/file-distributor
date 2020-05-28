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
    public void copyFile(String pathToFile, String mainDirPath) {
        Path srcFile = Paths.get(pathToFile);

        // line below extracts file name in order to create destination path
        String fileName = pathToFile.replace(mainDirPath, "").substring(1);
        Path dstFile = Paths.get("C:\\fd\\result copies\\" + fileName);

        try {
            Files.copy(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<String> getListOfPathsToFilesAndDirs(String mainDirPath) {
//        return Stream.of(new File(mainDirPath).listFiles())
////                .map(File::getName)
////                .collect(Collectors.toList());

        List<String> fileAndDirNamesList = Stream.of(new File(mainDirPath).listFiles())
                .map(File::getName)
                .collect(Collectors.toList());


        // loop creates paths to files and dirs
        for (int i = 0; i < fileAndDirNamesList.size(); i++){
            fileAndDirNamesList.set(i, mainDirPath + "\\" + fileAndDirNamesList.get(i));
        }

        return fileAndDirNamesList;

    }

    @Override
    public List<String> getListOfPathsToFilesOrDirs(String mainDirPath, int fileOrDir) {

        // loops in every case block creates paths to files and dirs

        switch (fileOrDir) {
            case 1:
                List<String> fileNamesList = Stream.of(new File(mainDirPath).listFiles())
                        .filter(file -> file.isFile())
                        .map(File::getName)
                        .collect(Collectors.toList());
                for (int i = 0; i < fileNamesList.size(); i++){
                    fileNamesList.set(i, mainDirPath + "\\" + fileNamesList.get(i));
                }
                return fileNamesList;

            case 2:
                List<String> dirNamesList = Stream.of(new File(mainDirPath).listFiles())
                        .filter(file -> file.isDirectory())
                        .map(File::getName)
                        .collect(Collectors.toList());
                for (int i = 0; i < dirNamesList.size(); i++){
                    dirNamesList.set(i, mainDirPath + "\\" + dirNamesList.get(i));
                }
                return dirNamesList;

            case 3:
                List<String> fileAndDirNamesList = Stream.of(new File(mainDirPath).listFiles())
                        .map(File::getName)
                        .collect(Collectors.toList());

                for (int i = 0; i < fileAndDirNamesList.size(); i++){
                    fileAndDirNamesList.set(i, mainDirPath + "\\" + fileAndDirNamesList.get(i));
                }
                return fileAndDirNamesList;

            default:
                return null;
        }
    }


    @Override
    public String findPathToLatestVDirectory(List<String> listOfAllFilesAndDirectories) {
        Pattern pattern = Pattern.compile("v[0-9][0-9]-");

        List<String> listOfvDirectories= listOfAllFilesAndDirectories.stream()
                .filter(pattern.asPredicate())
                .sorted()
                .collect(Collectors.toList());

        return listOfvDirectories.get(listOfvDirectories.size() - 1);
    }

    @Override
    public List<String> findPathsMatchingRegex(List<String> listOfAllFilesAndDirectories, String regex) {
        Pattern pattern = Pattern.compile(regex);

        List<String> listOfPaths= listOfAllFilesAndDirectories.stream()
                .filter(pattern.asPredicate())
                .sorted()
                .collect(Collectors.toList());

        return listOfPaths;
    }

}
