package spring.service;

import org.springframework.stereotype.Service;
import spring.cdrfiles.CdrFile;
import spring.repository.CdrFilesRepository;

import java.util.List;

@Service
public class CdrFilesServiceImpl implements CdrFilesService{

    private CdrFilesRepository cdrFilesRepository;

    public CdrFilesServiceImpl(CdrFilesRepository cdrFilesRepository) {
        this.cdrFilesRepository = cdrFilesRepository;
    }

    @Override
    public void create(CdrFile cdrFile) {

    }

    @Override
    public List<CdrFile> getAll() {
        return null;
    }

    @Override
    public void update(CdrFile cdrFile) {

    }

    @Override
    public void delete(CdrFile cdrFile) {

    }
}
