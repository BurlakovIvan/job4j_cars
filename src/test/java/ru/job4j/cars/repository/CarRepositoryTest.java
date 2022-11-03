package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CarRepository carRepository = new CarRepository(new CrudRepository(sf));

    @Test
    public void whenAddCar() {
        var car = new Car();
        car.setName("car");
        var rsl = carRepository.add(car);
        assertThat(rsl).isTrue();
        var result = carRepository.findById(car.getId());
        assertThat(result)
                .isPresent()
                .get().isEqualTo(car);
    }

    @Test
    public void whenUpdateCar() {
        var car = new Car();
        car.setName("car");
        var rsl = carRepository.add(car);
        assertThat(rsl).isTrue();
        car.setName("car1");
        rsl = carRepository.update(car);
        assertThat(rsl).isTrue();
        var result = carRepository.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName())
                .isEqualTo("car1");
    }

    @Test
    public void whenDeleteCar() {
        var car = new Car();
        car.setName("car");
        var rsl = carRepository.add(car);
        assertThat(rsl).isTrue();
        var result = carRepository.findById(car.getId());
        assertThat(result)
                .isPresent()
                .get().isEqualTo(car);
        carRepository.delete(car.getId());
        result = carRepository.findById(car.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenFindAllCar() {
        var car = new Car();
        car.setName("car");
        var rsl = carRepository.add(car);
        assertThat(rsl).isTrue();
        var car1 = new Car();
        car1.setName("car1");
        rsl = carRepository.add(car1);
        assertThat(rsl).isTrue();
        var result = carRepository.findAll();
        assertThat(result).isEqualTo(List.of(car, car1));
    }
}