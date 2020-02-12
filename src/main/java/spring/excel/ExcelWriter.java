package spring.excel;

import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.io.File;
import java.util.Set;

public interface ExcelWriter {

    File createAndFillExcelFile(Set<CdrFile> cdrFilesSet, RaksCode raksCode);
}
