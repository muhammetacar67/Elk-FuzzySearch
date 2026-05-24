package com.example.blogsearch.dto;

import lombok.Data;

@Data
public class BlogRequest {
    private String title;
    private String content;
    private String author;
    private String category;
}