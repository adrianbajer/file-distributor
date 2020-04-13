package spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.cdrfiles.CdrFile;
import spring.maps.Polygon;

import java.util.List;

@Repository
public interface PolygonRepository extends CrudRepository<Polygon, Integer> {

    @Override
    List<Polygon> findAll();
}

