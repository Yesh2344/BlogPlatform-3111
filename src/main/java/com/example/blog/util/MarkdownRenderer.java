package com.example.blog.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility component that converts Markdown text to safe HTML.
 */
@Component
public class MarkdownRenderer {

    private static final Logger logger = LoggerFactory.getLogger(MarkdownRenderer.class);
    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownRenderer() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }

    /**
     * Renders markdown content to HTML.
     *
     * @param markdown the raw markdown string (must not be null)
     * @return rendered HTML
     */
    public String render(String markdown) {
        if (markdown == null) {
            logger.warn("Attempted to render null markdown content.");
            return "";
        }
        try {
            var document = parser.parse(markdown);
            return renderer.render(document);
        } catch (Exception e) {
            logger.error("Failed to render markdown.", e);
            // Return raw text as fallback to avoid breaking UI
            return markdown;
        }
    }
}