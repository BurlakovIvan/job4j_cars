package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@ThreadSafe
@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    private final static String SELECT = """
                                         SELECT p FROM Post p
                                         JOIN FETCH p.priceHistories h
                                         JOIN FETCH p.car c
                                         JOIN FETCH p.participates participates
                                         """;
    private final static String SELECT_TO_DATE = String.format("%s WHERE p.created >= :fCreated", SELECT);
    private final static String SELECT_WITH_PHOTO = String.format("%s WHERE p.photo.size > 0", SELECT);

    private final static String SELECT_WITH_MODEL = String.format("%s WHERE p.carId = :fId", SELECT);

    public List<Post> postsToLastDay() {
        return crudRepository.query(SELECT_TO_DATE, Post.class,
                Map.of("fCreated", Timestamp.valueOf(LocalDateTime
                        .now()
                        .toLocalDate()
                        .atTime(LocalTime.MIN))));
    }

    public List<Post> postsWithPhotos() {
        return crudRepository.query(SELECT_WITH_PHOTO, Post.class);
    }

    public List<Post> postsWithModel(int carId) {
        return crudRepository.query(SELECT_WITH_MODEL, Post.class, Map.of("fId", carId));
    }

}
