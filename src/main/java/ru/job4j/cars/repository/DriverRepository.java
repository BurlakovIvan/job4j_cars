package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class DriverRepository {
    private static final Logger LOG = LogManager.getLogger(CarRepository.class.getName());
    private final CrudRepository crudRepository;
    private final static String SELECT = "SELECT d FROM Driver d";
    private final static String SELECT_BY_ID = "SELECT d FROM Driver d WHERE d.id = :fId";
    private final static String DELETE = "DELETE FROM Driver WHERE id = :fId";

    public boolean add(Driver driver) {
        var rsl = false;
        try {
            crudRepository.run(session -> session.save(driver));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    public List<Driver> findAll() {
        return crudRepository.query(SELECT, Driver.class);
    }

    public Optional<Driver> findById(int driverID) {
        return crudRepository.optional(SELECT_BY_ID, Driver.class,
                Map.of("fId", driverID));
    }

    public boolean update(Driver driver) {
        var rsl = false;
        try {
            crudRepository.run(session -> session.merge(driver));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    public boolean delete(int driverID) {
        var rsl = false;
        try {
            crudRepository.run(DELETE, Map.of("fId", driverID));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }
}
