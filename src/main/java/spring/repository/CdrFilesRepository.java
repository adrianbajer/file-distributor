package spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.cdrfiles.CdrFile;

import java.util.List;

@Repository
public interface CdrFilesRepository extends CrudRepository<CdrFile, Integer> {

    @Override
    List<CdrFile> findAll();
}
