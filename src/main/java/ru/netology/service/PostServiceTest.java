package ru.netology.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {
    private PostRepository repository;
    private PostService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PostRepository.class);
        service = new PostService(repository);
    }

    @Test
    void shouldReturnAllPosts() {
        List<Post> expectedPosts = List.of(new Post(1, "Post 1"), new Post(2, "Post 2"));
        when(repository.all()).thenReturn(expectedPosts);

        List<Post> actualPosts = service.all();

        assertEquals(expectedPosts, actualPosts);
        verify(repository).all();
    }

    @Test
    void shouldReturnPostById() {
        Post expectedPost = new Post(1, "Post 1");
        when(repository.getById(1)).thenReturn(Optional.of(expectedPost));

        Post actualPost = service.getById(1);

        assertEquals(expectedPost, actualPost);
        verify(repository).getById(1);
    }

    @Test
    void shouldThrowExceptionWhenPostNotFoundById() {
        when(repository.getById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(999));
        verify(repository).getById(999);
    }

    @Test
    void shouldSavePost() {
        Post post = new Post(0, "New Post");
        when(repository.save(post)).thenReturn(post);

        Post savedPost = service.save(post);

        assertEquals(post, savedPost);
        verify(repository).save(post);
    }

    @Test
    void shouldRemovePostById() {
        doNothing().when(repository).removeById(1);

        service.removeById(1);

        verify(repository).removeById(1);
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistentPost() {
        doThrow(new NotFoundException()).when(repository).removeById(999);

        assertThrows(NotFoundException.class, () -> service.removeById(999));
        verify(repository).removeById(999);
    }
}