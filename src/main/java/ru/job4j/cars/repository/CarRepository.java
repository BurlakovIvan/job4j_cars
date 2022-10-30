package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;

@ThreadSafe
@Repository
@AllArgsConstructor
public class CarRepository {
    private final CrudRepository crudRepository;

    private final static String SELECT = "SELECT c FROM Car c JOIN FETCH c.owners";
    private final static String SELECT_BY_ID = "SELECT c FROM Car c JOIN FETCH c.owners WHERE c.id = :fId";

    private final static String DELETE = "DELETE FROM Car WHERE id = :fId";

    public void add(Car car) {
        crudRepository.run(session -> session.save(car));
    }

    public List<Car> findAll() {
        return crudRepository.query(SELECT, Car.class);
    }

    public Car findById(int carID) {
        return crudRepository.optional(SELECT_BY_ID, Car.class, Map.of("fId", carID)).orElse(null);
    }

    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    public void delete(int carID) {
        crudRepository.run(DELETE, Map.of("fId", carID));
    }
}
