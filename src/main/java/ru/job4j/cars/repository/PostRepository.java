package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOG = LogManager.getLogger(CarRepository.class.getName());
    private final static String SELECT = """
                                         SELECT p FROM Post p
                                         LEFT JOIN FETCH p.priceHistories price
                                         LEFT JOIN FETCH p.car c
                                         LEFT JOIN FETCH p.participates participates
                                         """;
    private final static String SELECT_TO_DATE = String
            .format("%s WHERE p.created BETWEEN :fStart AND :fEnd", SELECT);
    private final static String SELECT_WITH_PHOTO = String.format("%s WHERE p.photo IS NOT NULL", SELECT);

    private final static String SELECT_WITH_MODEL = String.format("%s WHERE c.id = :fId", SELECT);

    private final static String SELECT_BY_ID = "SELECT p FROM Post p WHERE p.id = :fId";

    public boolean add(Post post) {
        var rsl = false;
        try {
            crudRepository.run(session -> session.save(post));
            rsl = true;
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return rsl;
    }

    public Optional<Post> findById(int postID) {
        return crudRepository.optional(SELECT_BY_ID, Post.class,
                Map.of("fId", postID));
    }

    public List<Post> postsToLastDay() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(1L);
        return crudRepository.query(SELECT_TO_DATE, Post.class,
                Map.of("fStart", start, "fEnd", end));
    }

    public List<Post> postsWithPhotos() {
        return crudRepository.query(SELECT_WITH_PHOTO, Post.class);
    }

    public List<Post> postsWithModel(int carId) {
        return crudRepository.query(SELECT_WITH_MODEL, Post.class,
                Map.of("fId", carId));
    }

}
