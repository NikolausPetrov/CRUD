package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest {
    private PostRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PostRepository();
    }

    @Test
    void shouldSaveNewPost() {
        Post post = new Post(0, "New Post");
        Post savedPost = repository.save(post);

        assertEquals(1, savedPost.getId());
        assertEquals("New Post", savedPost.getContent());
    }

    @Test
    void shouldUpdateExistingPost() {
        Post post = new Post(0, "New Post");
        Post savedPost = repository.save(post);

        savedPost.setContent("Updated Post");
        Post updatedPost = repository.save(savedPost);

        assertEquals(savedPost.getId(), updatedPost.getId());
        assertEquals("Updated Post", updatedPost.getContent());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentPost() {
        Post post = new Post(999, "Non-existent Post");

        assertThrows(NotFoundException.class, () -> repository.save(post));
    }

    @Test
    void shouldRemovePostById() {
        Post post = new Post(0, "New Post");
        Post savedPost = repository.save(post);

        repository.removeById(savedPost.getId());

        assertTrue(repository.getById(savedPost.getId()).isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistentPost() {
        assertThrows(NotFoundException.class, () -> repository.removeById(999));
    }

    @Test
    void shouldReturnAllPosts() {
        Post post1 = new Post(0, "Post 1");
        Post post2 = new Post(0, "Post 2");
        repository.save(post1);
        repository.save(post2);

        List<Post> posts = repository.all();

        assertEquals(2, posts.size());
    }
}