package com.example.blogsearch.service;

import com.example.blogsearch.dto.BlogRequest;
import com.example.blogsearch.dto.BlogResponse;
import com.example.blogsearch.model.document.BlogDocument;
import com.example.blogsearch.model.entity.Blog;
import com.example.blogsearch.repository.es.BlogEsRepository;
import com.example.blogsearch.repository.jpa.BlogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogJpaRepository blogJpaRepository;
    private final BlogEsRepository blogEsRepository;

    @Transactional
    public BlogResponse save(BlogRequest request) {

        // 1. PostgreSQL'e kaydet
        Blog blog = Blog.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .category(request.getCategory())
                .createdAt(LocalDateTime.now())
                .build();

        Blog savedBlog = blogJpaRepository.save(blog);

        // 2. ES'e kaydet
        BlogDocument document = BlogDocument.builder()
                .id(savedBlog.getId())
                .title(savedBlog.getTitle())
                .content(savedBlog.getContent())
                .author(savedBlog.getAuthor())
                .category(savedBlog.getCategory())
                .build();

        blogEsRepository.save(document);

        // 3. Response dön
        return toResponse(savedBlog);
    }

    public List<BlogResponse> searchByTitle(String title) {
        return blogEsRepository.findByTitle(title)
                .stream()
                .map(doc -> BlogResponse.builder()
                        .id(doc.getId())
                        .title(doc.getTitle())
                        .content(doc.getContent())
                        .author(doc.getAuthor())
                        .category(doc.getCategory())
                        .build())
                .toList();
    }

    public List<BlogResponse> searchByCategory(String category) {
        return blogEsRepository.findByCategory(category)
                .stream()
                .map(doc -> BlogResponse.builder()
                        .id(doc.getId())
                        .title(doc.getTitle())
                        .content(doc.getContent())
                        .author(doc.getAuthor())
                        .category(doc.getCategory())
                        .build())
                .toList();
    }

    public List<BlogResponse> getAll() {
        return blogJpaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BlogResponse> fuzzySearchByTitle(String title) {
        return blogEsRepository.findByTitleFuzzy(title)
                .stream()
                .map(doc -> BlogResponse.builder()
                        .id(doc.getId())
                        .title(doc.getTitle())
                        .content(doc.getContent())
                        .author(doc.getAuthor())
                        .category(doc.getCategory())
                        .build())
                .toList();
    }

    private BlogResponse toResponse(Blog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .author(blog.getAuthor())
                .category(blog.getCategory())
                .createdAt(blog.getCreatedAt())
                .build();
    }
}