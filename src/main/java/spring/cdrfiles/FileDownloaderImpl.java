package spring.cdrfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileDownloaderImpl implements FileDownloader {

    @Override
    public void copyFile(CdrFile cdrFile) {

        PathCreator pathCreator = new PathCreatorImpl();

        Path srcFile = Paths.get(pathCreator.createPath(cdrFile));
        Path dstFile = Paths.get("src\\main\\resources\\result copies\\" + cdrFile.getName() + ".txt");

        try {
            Files.copy(srcFile, dstFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
