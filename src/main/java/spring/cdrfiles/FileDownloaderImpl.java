package spring.cdrfiles;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;

public class FileDownloaderImpl implements FileDownloader {

    @Override
    public void copyFile(Set<CdrFile> cdrFileSet) {
        PathCreator pathCreator = new PathCreatorImpl();

        for(CdrFile cdrFile : cdrFileSet) {

            Path srcFile = Paths.get(pathCreator.createPath(cdrFile));
            Path dstFile = Paths.get("C:\\Users\\Public\\" + cdrFile.getName() + ".txt");

            try {
                Files.copy(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path srcFile = Paths.get("src\\main\\resources\\excelfiles\\downloaded_files_data.xls");
        Path dstFile = Paths.get("C:\\Users\\Public\\downloaded_files_data.xls");

        try {
            Files.copy(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
