package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;

@ThreadSafe
@Repository
@AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    private final static String SELECT = "SELECT c FROM Engine c";
    private final static String SELECT_BY_ID = "SELECT c FROM Engine c WHERE c.id = :fId";

    public void add(Engine engine) {
        crudRepository.run(session -> session.save(engine));
    }

    public List<Engine> findAll() {
        return crudRepository.query(SELECT, Engine.class);
    }

    public Engine findById(int engineID) {
        return crudRepository.optional(SELECT_BY_ID, Engine.class, Map.of("fId", engineID)).orElse(null);
    }

    public void update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }
}
