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
        cdrFilesRepository.save(cdrFile);
    }

    @Override
    public List<CdrFile> getAll() {
        return cdrFilesRepository.findAll();
    }

    @Override
    public void update(CdrFile cdrFile) {
        cdrFilesRepository.save(cdrFile);
    }

    @Override
    public void delete(CdrFile cdrFile) {
        cdrFilesRepository.delete(cdrFile);
    }
}
