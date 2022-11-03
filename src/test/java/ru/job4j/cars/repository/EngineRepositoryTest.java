package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class EngineRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final EngineRepository engineRepository = new EngineRepository(new CrudRepository(sf));

    @Test
    public void whenAddEngine() {
        var engine = new Engine();
        engine.setName("engine");
        var rsl = engineRepository.add(engine);
        assertThat(rsl).isTrue();
        var result = engineRepository.findById(engine.getId());
        assertThat(result)
                .isPresent()
                .get().isEqualTo(engine);
    }

    @Test
    public void whenUpdateEngine() {
        var engine = new Engine();
        engine.setName("engine");
        var rsl = engineRepository.add(engine);
        assertThat(rsl).isTrue();
        engine.setName("engine1");
        rsl = engineRepository.update(engine);
        assertThat(rsl).isTrue();
        var result = engineRepository.findById(engine.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("engine1");
    }

    @Test
    public void whenFindAllEngine() {
        var engine = new Engine();
        engine.setName("engine");
        var rsl = engineRepository.add(engine);
        assertThat(rsl).isTrue();
        var engine1 = new Engine();
        engine1.setName("engine1");
        rsl = engineRepository.add(engine1);
        assertThat(rsl).isTrue();
        var result = engineRepository.findAll();
        assertThat(result).isEqualTo(List.of(engine, engine1));
    }
}