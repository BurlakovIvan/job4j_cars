package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;

@ThreadSafe
@Repository
@AllArgsConstructor
public class DriverRepository {
    private final CrudRepository crudRepository;

    private final static String SELECT = "SELECT d FROM Driver d";
    private final static String SELECT_BY_ID = "SELECT d FROM Driver d WHERE d.id = :fId";

    private final static String DELETE = "DELETE FROM Driver WHERE id = :fId";

    public void add(Driver driver) {
        crudRepository.run(session -> session.save(driver));
    }

    public List<Driver> findAll() {
        return crudRepository.query(SELECT, Driver.class);
    }

    public Driver findById(int driverID) {
        return crudRepository.optional(SELECT_BY_ID, Driver.class, Map.of("fId", driverID)).orElse(null);
    }

    public void update(Driver driver) {
        crudRepository.run(session -> session.merge(driver));
    }

    public void delete(int driverID) {
        crudRepository.run(DELETE, Map.of("fId", driverID));
    }
}
