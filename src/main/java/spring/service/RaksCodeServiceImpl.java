package spring.service;

import org.springframework.stereotype.Service;
import spring.rakscode.RaksCode;
import spring.repository.RaksCodesRepository;

import java.util.List;

@Service
public class RaksCodeServiceImpl implements RaksCodeService {

    private RaksCodesRepository raksCodesRepository;

    public RaksCodeServiceImpl(RaksCodesRepository raksCodesRepository) {
        this.raksCodesRepository = raksCodesRepository;
    }

    @Override
    public void create(RaksCode raksCode) {
        raksCodesRepository.save(raksCode);
    }

    @Override
    public List<RaksCode> getAll() {
        return raksCodesRepository.findAll();
    }

    @Override
    public void update(RaksCode raksCode) {
        raksCodesRepository.save(raksCode);
    }

    @Override
    public void delete(RaksCode raksCode) {
        raksCodesRepository.delete(raksCode);
    }
}
