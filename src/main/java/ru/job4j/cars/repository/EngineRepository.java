package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class EngineRepository {

    private static final Logger LOG = LogManager.getLogger(CarRepository.class.getName());
    private final CrudRepository crudRepository;
    private final static String SELECT = "SELECT c FROM Engine c";
    private final static String SELECT_BY_ID = "SELECT c FROM Engine c WHERE c.id = :fId";

    public boolean add(Engine engine) {
        var rsl = false;
        try {
            crudRepository.run(session -> session.save(engine));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    public List<Engine> findAll() {
        return crudRepository.query(SELECT, Engine.class);
    }

    public Optional<Engine> findById(int engineID) {
        return crudRepository.optional(SELECT_BY_ID, Engine.class,
                Map.of("fId", engineID));
    }

    public boolean update(Engine engine) {
        var rsl = false;
        try {
            crudRepository.run(session -> session.merge(engine));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }
}
