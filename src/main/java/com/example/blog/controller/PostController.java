package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller exposing blog post endpoints.
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        String content = payload.get("content");
        if (title == null || content == null) {
            log.warn("Invalid create request payload: {}", payload);
            return ResponseEntity.badRequest().build();
        }
        Post saved = postService.createPost(title, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", saved.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable Long id) {
        return postService.getPost(id)
                .map(post -> {
                    String html = postService.renderPostToHtml(post);
                    return ResponseEntity.ok()
                            .contentType(MediaType.TEXT_HTML)
                            .body(html);
                })
                .orElseGet(() -> {
                    log.warn("Post not found: {}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @GetMapping
    public ResponseEntity<List<Post>> list() {
        List<Post> posts = postService.listPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        String content = payload.get("content");
        return postService.updatePost(id, title, content)
                .map(updated -> ResponseEntity.noContent().build())
                .orElseGet(() -> {
                    log.warn("Attempted to update non‑existent post id {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}