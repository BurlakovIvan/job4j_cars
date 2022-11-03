package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final UserRepository userRepository = new UserRepository(new CrudRepository(sf));

    @Test
    public void whenAddNewUserThenTheSame() {
        var user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        var rsl = userRepository.create(user);
        assertThat(rsl).isEqualTo(user);
        var result = userRepository.findById(user.getId());
        assertThat(result)
                .isPresent().get()
                .isEqualTo(user);
    }

    @Test
    public void whenUpdateUserThenHasSamePassword() {
        var user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        userRepository.create(user);
        user.setPassword("user");
        userRepository.update(user);
        var rsl = userRepository.findById(user.getId());
        assertThat(rsl.get().getPassword())
                .isEqualTo("user");
    }

    @Test
    public void whenDeleteUserThenFalse() {
        var user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        userRepository.create(user);
        var rsl = userRepository.findById(user.getId());
        assertThat(rsl).isPresent()
                .get().isEqualTo(user);
        userRepository.delete(user.getId());
        var rsl1 = userRepository.findById(user.getId());
        assertThat(rsl1.isPresent()).isFalse();
    }

    @Test
    public void whenFindAllOrderById() {
        var user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        userRepository.create(user);
        var user1 = new User();
        user1.setLogin("user");
        user1.setPassword("user");
        userRepository.create(user1);
        var rsl = userRepository.findAllOrderById();
        assertThat(rsl).isEqualTo(List.of(user, user1));
    }

    @Test
    public void whenFindById() {
        var user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        userRepository.create(user);
        var user1 = new User();
        user1.setLogin("user");
        user1.setPassword("user");
        userRepository.create(user1);
        var rsl = userRepository.findById(user1.getId());
        assertThat(rsl.get()).isEqualTo(user1);
    }

    @Test
    public void whenFindByLogin() {
        var user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        userRepository.create(user);
        var user1 = new User();
        user1.setLogin("user");
        user1.setPassword("user");
        userRepository.create(user1);
        var rsl = userRepository.findByLogin("admin");
        assertThat(rsl)
                .isPresent()
                .get().isEqualTo(user);
    }
}