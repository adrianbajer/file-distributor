package spring.excel;

import org.springframework.web.multipart.MultipartFile;
import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.util.Set;


public interface ExcelParser {

    Set<CdrFile> getSetOfCdrFiles(RaksCode raksCode);

//    Set<CdrFile> readDataFromFile(MultipartFile file);

}
