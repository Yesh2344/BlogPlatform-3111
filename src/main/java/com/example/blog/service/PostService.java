package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.util.MarkdownRenderer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer containing business logic for blog posts.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final MarkdownRenderer markdownRenderer;

    /**
     * Creates a new post.
     *
     * @param title   post title
     * @param content markdown content
     * @return persisted {@link Post}
     */
    public Post createPost(String title, String content) {
        logger.info("Creating post with title: {}", title);
        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();
        return postRepository.save(post);
    }

    /**
     * Retrieves a post by ID.
     *
     * @param id post identifier
     * @return optional containing the post if found
     */
    public Optional<Post> getPost(Long id) {
        logger.debug("Fetching post with id {}", id);
        return postRepository.findById(id);
    }

    /**
     * Returns all posts ordered by creation date descending.
     *
     * @return list of posts
     */
    public List<Post> listPosts() {
        logger.debug("Listing all posts");
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Updates an existing post.
     *
     * @param id      post identifier
     * @param title   new title (optional)
     * @param content new markdown content (optional)
     * @return updated post or empty if not found
     */
    public Optional<Post> updatePost(Long id, String title, String content) {
        return postRepository.findById(id).map(existing -> {
            if (title != null && !title.isBlank()) {
                existing.setTitle(title);
            }
            if (content != null && !content.isBlank()) {
                existing.setContent(content);
            }
            logger.info("Updating post id {}", id);
            return postRepository.save(existing);
        });
    }

    /**
     * Deletes a post by ID.
     *
     * @param id post identifier
     * @return true if deleted, false if not found
     */
    public boolean deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            logger.warn("Attempted to delete non‑existent post id {}", id);
            return false;
        }
        postRepository.deleteById(id);
        logger.info("Deleted post id {}", id);
        return true;
    }

    /**
     * Renders the markdown content of a post to HTML.
     *
     * @param post the {@link Post}
     * @return HTML string
     */
    public String renderPostToHtml(Post post) {
        return markdownRenderer.render(post.getContent());
    }
}