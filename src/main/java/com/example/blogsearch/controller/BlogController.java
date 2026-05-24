package com.example.blogsearch.controller;

import com.example.blogsearch.dto.BlogRequest;
import com.example.blogsearch.dto.BlogResponse;
import com.example.blogsearch.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogResponse> save(@RequestBody BlogRequest request) {
        return ResponseEntity.ok(blogService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<BlogResponse>> getAll() {
        return ResponseEntity.ok(blogService.getAll());
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<BlogResponse>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(blogService.searchByTitle(title));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<BlogResponse>> searchByCategory(@RequestParam String category) {
        return ResponseEntity.ok(blogService.searchByCategory(category));
    }

    @GetMapping("/search/fuzzy")
    public ResponseEntity<List<BlogResponse>> fuzzySearch(@RequestParam String title) {
        return ResponseEntity.ok(blogService.fuzzySearchByTitle(title));
    }
}
