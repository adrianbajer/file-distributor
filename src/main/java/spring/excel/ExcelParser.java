package spring.excel;

import spring.plikizrodlowe.PlikZrodlowy;
import spring.rakscode.RaksCode;

import java.util.List;
import java.util.Set;

public interface ExcelParser {

    Set<PlikZrodlowy> getSetOfPlikiZrodlowe(RaksCode raksCode);

}
