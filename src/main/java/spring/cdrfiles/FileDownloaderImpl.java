package spring.cdrfiles;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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

@Component
public class FileDownloaderImpl implements FileDownloader {

    private String resultCopiesDirPath;

    public FileDownloaderImpl() {
    }

    @Autowired
    public FileDownloaderImpl(@Qualifier("resultCopiesDirPath") String resultCopiesDirPath) {
        this.resultCopiesDirPath = resultCopiesDirPath;
    }

    @Override
    public void copyFileFromMainDir(String pathToFile) {

        Path srcFile = Paths.get(pathToFile);

        //        following code creates separate dirs for files from different projects
        String toCreateDstDirName = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
        String dstDirName = toCreateDstDirName.substring(toCreateDstDirName.lastIndexOf("\\") + 1);

        // line below extracts file name in order to create destination path
        String fileName = pathToFile.substring(pathToFile.lastIndexOf("\\"));
        Path dstFile = Paths.get(resultCopiesDirPath + dstDirName + "\\" + fileName);

        try {
            Files.copy(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void copyFileFromLatestVDir(String pathToFile) {
        Path srcFile = Paths.get(pathToFile);

        //        following code creates separate dirs for files from different projects, in case of v dir we have to
        //        repeat cutting off last dir name twice
        String toCreateDstDirName1 = pathToFile.substring(0, pathToFile.lastIndexOf("\\"));
        String toCreateDstDirName2 = toCreateDstDirName1.substring(0, toCreateDstDirName1.lastIndexOf("\\"));
        String dstDirName = toCreateDstDirName2.substring(toCreateDstDirName2.lastIndexOf("\\") + 1);

        // line below extracts file name in order to create destination path
        String fileName = pathToFile.substring(pathToFile.lastIndexOf("\\"));
        Path dstFile = Paths.get(resultCopiesDirPath + dstDirName + "\\" + fileName);

        try {
            Files.copy(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void copyDirFromMainDir(String pathToDir) {
        File srcDir = new File(pathToDir);

//        following code creates separate dirs for dirs with different source
        String toCreateDstDirName = pathToDir.substring(0, pathToDir.lastIndexOf("\\"));
        String dstDirName = toCreateDstDirName.substring(toCreateDstDirName.lastIndexOf("\\") + 1);
        String copiedDirName = pathToDir.substring(pathToDir.lastIndexOf("\\") + 1);
        File dstDir = new File(resultCopiesDirPath + dstDirName + "\\" + copiedDirName);

        try {
            FileUtils.copyDirectory(srcDir, dstDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void copyDirFromLatestVDir(String pathToDir) {
        File srcDir = new File(pathToDir);

//        following code creates separate dirs for dirs with different source, in case of v dir we have to
//        repeat cutting off last dir name twice
        String toCreateDstDirName1 = pathToDir.substring(0, pathToDir.lastIndexOf("\\"));
        String toCreateDstDirName2 = toCreateDstDirName1.substring(0, toCreateDstDirName1.lastIndexOf("\\"));
        String dstDirName = toCreateDstDirName2.substring(toCreateDstDirName2.lastIndexOf("\\") + 1);
        String copiedDirName = pathToDir.substring(pathToDir.lastIndexOf("\\") + 1);
        File dstDir = new File(resultCopiesDirPath + dstDirName + "\\" + copiedDirName);

        try {
            FileUtils.copyDirectory(srcDir, dstDir);
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

        // loops in every case block creates paths to files or dirs
//        possible cases: 1- files, 2 -dirs, 3 -files and dirs

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
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        List<String> listOfPaths= listOfAllFilesAndDirectories.stream()
                .filter(pattern.asPredicate())
                .sorted()
                .collect(Collectors.toList());

        return listOfPaths;
    }

}
