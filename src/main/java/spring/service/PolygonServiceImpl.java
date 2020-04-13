package spring.service;

import org.springframework.stereotype.Service;
import spring.maps.Polygon;
import spring.repository.PolygonRepository;

import java.util.List;

@Service
public class PolygonServiceImpl implements PolygonService {

    private PolygonRepository polygonRepository;

    public PolygonServiceImpl(PolygonRepository polygonRepository) {
        this.polygonRepository = polygonRepository;
    }

    @Override
    public void create(Polygon polygon) {
        polygonRepository.save(polygon);
    }

    @Override
    public List<Polygon> getAll() {
        return polygonRepository.findAll();
    }

    @Override
    public void update(Polygon polygon) {
        polygonRepository.save(polygon);
    }

    @Override
    public void delete(Polygon polygon) {
        polygonRepository.delete(polygon);
    }
}
