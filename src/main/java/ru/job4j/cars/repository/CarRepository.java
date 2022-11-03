package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@ThreadSafe
@Repository
@AllArgsConstructor
public class CarRepository {

    private static final Logger LOG = LogManager.getLogger(CarRepository.class.getName());
    private final CrudRepository crudRepository;

    private final static String SELECT = "SELECT c FROM Car c LEFT JOIN FETCH c.owners o";
    private final static String SELECT_BY_ID = """
                                               SELECT c FROM Car c
                                               LEFT JOIN FETCH c.owners o
                                               WHERE c.id = :fId
                                               """;

    private final static String DELETE = "DELETE FROM Car WHERE id = :fId";

    public boolean add(Car car) {
        var rsl = false;
        try {
            crudRepository.run(session -> session.persist(car));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    public List<Car> findAll() {
        return crudRepository.query(SELECT, Car.class);
    }

    public Optional<Car> findById(int carID) {
        return crudRepository.optional(SELECT_BY_ID, Car.class,
                Map.of("fId", carID));
    }

    public boolean update(Car car) {
        var rsl = false;
        try {
            crudRepository.run(session -> session.merge(car));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    public boolean delete(int carID) {
        var rsl = false;
        try {
            crudRepository.run(DELETE, Map.of("fId", carID));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }
}
