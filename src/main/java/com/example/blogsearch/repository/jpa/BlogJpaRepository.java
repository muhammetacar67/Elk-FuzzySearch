package com.example.blogsearch.repository.jpa;

import com.example.blogsearch.model.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogJpaRepository extends JpaRepository<Blog, String> {

    List<Blog> findByCategory(String category);

    List<Blog> findByAuthor(String author);
}