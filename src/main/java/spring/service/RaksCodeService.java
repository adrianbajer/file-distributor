package spring.service;

import spring.rakscode.RaksCode;

import java.util.List;

public interface RaksCodeService {

    void create(RaksCode raksCode);

    List<RaksCode> getAll();

    void update(RaksCode raksCode);

    void delete(RaksCode raksCode);
}
