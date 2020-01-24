package spring.cdrfiles;

import java.util.Set;

public interface FileDownloader {

    void copyFile(Set<CdrFile> cdrFileSet);

}
