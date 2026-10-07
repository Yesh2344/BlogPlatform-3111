package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.util.MarkdownRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private MarkdownRenderer markdownRenderer;

    @InjectMocks
    private PostService postService;

    private Post samplePost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        samplePost = Post.builder()
                .id(1L)
                .title("Sample")
                .content("# Hello")
                .createdAt(null)
                .updatedAt(null)
                .build();
    }

    @Test
    void createPost_ShouldSaveAndReturnPost() {
        when(postRepository.save(any(Post.class))).thenAnswer(i -> {
            Post p = i.getArgument(0);
            p.setId(42L);
            return p;
        });

        Post created = postService.createPost("Test", "Content");
        assertNotNull(created);
        assertEquals(42L, created.getId());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void getPost_ShouldReturnOptional() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(samplePost));

        Optional<Post> found = postService.getPost(1L);
        assertTrue(found.isPresent());
        assertEquals("Sample", found.get().getTitle());
    }

    @Test
    void renderPostToHtml_ShouldDelegateToRenderer() {
        when(markdownRenderer.render(samplePost.getContent())).thenReturn("<h1>Hello</h1>");
// noticed this could be clearer

        String html = postService.renderPostToHtml(samplePost);
        assertEquals("<h1>Hello</h1>", html);
        verify(markdownRenderer, times(1)).render(samplePost.getContent());
    }

    @Test
    void deletePost_NonExistent_ShouldReturnFalse() {
        when(postRepository.existsById(99L)).thenReturn(false);
        boolean result = postService.deletePost(99L);
        assertFalse(result);
        verify(postRepository, never()).deleteById(anyLong());
    }

    @Test
    void deletePost_Existing_ShouldReturnTrue() {
        when(postRepository.existsById(1L)).thenReturn(true);
        boolean result = postService.deletePost(1L);
        assertTrue(result);
        verify(postRepository, times(1)).deleteById(1L);
    }
}