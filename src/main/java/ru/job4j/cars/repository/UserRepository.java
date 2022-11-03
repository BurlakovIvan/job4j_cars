package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class UserRepository {

    private static final Logger LOG = LogManager.getLogger(CarRepository.class.getName());
    private final CrudRepository crudRepository;
    private static final String SELECT = "SELECT u FROM User u";
    private static final String DELETE = "DELETE FROM User WHERE id = :fId";
    private static final String SELECT_BY_ID = "SELECT u FROM User u WHERE u.id = :fId";
    private static final String SELECT_BY_LOGIN_LIKE = "SELECT u FROM User u WHERE u.login LIKE :fKey";
    private static final String SELECT_BY_LOGIN = "SELECT u FROM User u WHERE u.login = :fLogin";
    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        User rsl = new User();
        try {
            crudRepository.run(session -> session.persist(user));
            rsl = user;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     * @return true пользователь обновлен успешно, иначе false.
     */
    public boolean update(User user) {
        var rsl = false;
        try {
            crudRepository.run(session -> session.merge(user));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     * @return true пользователь успешно удален, иначе false.
     */
    public boolean delete(int userId) {
        var rsl = false;
        try {
            crudRepository.run(DELETE, Map.of("fId", userId));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    /**
     * Список пользователей отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query(SELECT, User.class);
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        return crudRepository.optional(SELECT_BY_ID, User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(SELECT_BY_LOGIN_LIKE, User.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return пользователь.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(SELECT_BY_LOGIN, User.class,
                Map.of("fLogin", login)
        );
    }
}
