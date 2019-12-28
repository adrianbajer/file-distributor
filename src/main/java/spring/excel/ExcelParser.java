package spring.excel;

import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.util.Set;


public interface ExcelParser {

    Set<CdrFile> getSetOfCdrFiles(RaksCode raksCode);

}
