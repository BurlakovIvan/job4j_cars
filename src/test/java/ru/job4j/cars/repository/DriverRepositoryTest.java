package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Driver;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DriverRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final DriverRepository driverRepository = new DriverRepository(new CrudRepository(sf));

    @Test
    public void whenAddDriver() {
        var driver = new Driver();
        driver.setName("driver");
        var rsl = driverRepository.add(driver);
        assertThat(rsl).isTrue();
        var result = driverRepository.findById(driver.getId());
        assertThat(result)
                .isPresent()
                .get().isEqualTo(driver);
    }

    @Test
    public void whenUpdateDriver() {
        var driver = new Driver();
        driver.setName("driver");
        driverRepository.add(driver);
        driver.setName("driver1");
        var rsl = driverRepository.update(driver);
        assertThat(rsl).isTrue();
        var result = driverRepository.findById(driver.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName())
                .isEqualTo("driver1");
    }

    @Test
    public void whenDeleteDriver() {
        var driver = new Driver();
        driver.setName("driver");
        var rsl = driverRepository.add(driver);
        assertThat(rsl).isTrue();
        var result = driverRepository.findById(driver.getId());
        assertThat(result)
                .isPresent()
                .get().isEqualTo(driver);
        driverRepository.delete(driver.getId());
        result = driverRepository.findById(driver.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenFindAllDriver() {
        var driver = new Driver();
        driver.setName("driver");
        var rsl = driverRepository.add(driver);
        assertThat(rsl).isTrue();
        var driver1 = new Driver();
        driver1.setName("driver1");
        rsl = driverRepository.add(driver1);
        assertThat(rsl).isTrue();
        var result = driverRepository.findAll();
        assertThat(result).isEqualTo(List.of(driver, driver1));
    }
}