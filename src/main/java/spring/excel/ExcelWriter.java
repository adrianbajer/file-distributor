package spring.excel;

import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.util.Set;

public interface ExcelWriter {

    void saveChangesToExcelFile(Set<CdrFile> cdrFilesSet, RaksCode raksCode);
}
