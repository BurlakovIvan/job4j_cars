package ru.job4j.cars.repository;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PostRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final CrudRepository crudRepository = new CrudRepository(new MetadataSources(registry)
            .buildMetadata().buildSessionFactory());
    private final PostRepository postRepository = new PostRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);

    @Test
    public void whenAddNewPostThenTheSame() {
        var post = new Post();
        post.setText("text");
        var result = postRepository.add(post);
        assertThat(result).isTrue();
        var rsl = postRepository.findById(post.getId());
        assertThat(rsl)
                .isPresent()
                .get().isEqualTo(post);
    }

    @Test
    public void getAllPostsWithNotNullPhoto() {
        Post post = new Post();
        Post post1 = new Post();
        Post post2 = new Post();
        byte[] photo = new byte[] {1, 1, 1, 1};
        byte[] photo1 = new byte[] {1, 1, 1, 0};
        post1.setPhoto(photo);
        post.setPhoto(photo1);
        post.setText("text");
        post1.setText("text1");
        post2.setText("without photo");
        var rsl = postRepository.add(post);
        assertThat(rsl).isTrue();
        rsl = postRepository.add(post1);
        assertThat(rsl).isTrue();
        rsl = postRepository.add(post2);
        assertThat(rsl).isTrue();
        List<Post> resultList = postRepository.postsWithPhotos();
        assertThat(resultList).isEqualTo(List.of(post, post1));
    }

    @Test
    public void getAllPostsToLastDay() {
        Post post = new Post();
        Post post1 = new Post();
        Post post2 = new Post();
        post.setCreated(LocalDateTime.now());
        post1.setCreated(LocalDateTime.now().minusDays(2));
        post2.setCreated(LocalDateTime.now());
        post.setText("text");
        post1.setText("minus two days");
        post2.setText("text2");
        var rsl = postRepository.add(post);
        assertThat(rsl).isTrue();
        rsl = postRepository.add(post1);
        assertThat(rsl).isTrue();
        rsl = postRepository.add(post2);
        assertThat(rsl).isTrue();
        List<Post> resultList = postRepository.postsToLastDay();
        assertThat(resultList).isEqualTo(List.of(post, post2));
    }

    @Test
    public void getAllPostsModelCar() {
        Post post = new Post();
        Post post1 = new Post();
        Post post2 = new Post();
        Car car = new Car();
        Car car1 = new Car();
        car.setName("car");
        car1.setName("car1");
        var rsl = carRepository.add(car1);
        assertThat(rsl).isTrue();
        rsl = carRepository.add(car);
        assertThat(rsl).isTrue();
        post.setText("text");
        post.setCar(car);
        post1.setText("text1");
        post1.setCar(car1);
        post2.setText("text2");
        post2.setCar(car1);
        rsl = postRepository.add(post);
        assertThat(rsl).isTrue();
        rsl = postRepository.add(post1);
        assertThat(rsl).isTrue();
        rsl = postRepository.add(post2);
        assertThat(rsl).isTrue();
        List<Post> resultList = postRepository.postsWithModel(car1.getId());
        assertThat(resultList).isEqualTo(List.of(post1, post2));
    }
}