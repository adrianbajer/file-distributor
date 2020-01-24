package spring.repository;

import spring.cdrfiles.CdrFile;
import spring.rakscode.RaksCode;

import java.util.Set;

public interface DataStorage {

    public Set<CdrFile> getSetOfCdrFiles(RaksCode raksCode);
}
