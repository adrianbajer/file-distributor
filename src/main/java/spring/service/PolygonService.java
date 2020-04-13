package spring.service;

import spring.cdrfiles.CdrFile;
import spring.maps.Polygon;

import java.util.List;

public interface PolygonService {
    void create(Polygon polygon);

    List<Polygon> getAll();

    void update(Polygon polygon);

    void delete(Polygon polygon);
}
