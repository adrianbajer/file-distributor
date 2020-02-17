package spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.rakscode.RaksCode;

import java.util.List;

@Repository
public interface RaksCodesRepository extends CrudRepository<RaksCode, Long> {

    @Override
    List<RaksCode> findAll();
}