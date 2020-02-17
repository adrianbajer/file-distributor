package spring.service;

import spring.cdrfiles.CdrFile;

import java.util.List;

public interface CdrFilesService {

    void create(CdrFile cdrFile);

    List<CdrFile> getAll();

    void update(CdrFile cdrFile);

    void delete(CdrFile cdrFile);
}
