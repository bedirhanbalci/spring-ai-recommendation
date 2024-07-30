package com.patika.bloghubservice.repository;


import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.model.enums.BlogStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BlogRepository {

    private final Map<String, Blog> blogMap = new HashMap<>();

    public void save(Blog blog) {
        blogMap.put(blog.getTitle(), blog);
    }

    public Optional<Blog> findByTitle(String title) {
        return blogMap.values()
                .stream()
                .filter(blog -> blog.getTitle().equals(title))
                .filter(blog -> !blog.getBlogStatus().equals(BlogStatus.DELETED))
                .findFirst();
    }

    public List<Blog> findAll() {
        return blogMap.values().stream().toList();
    }

    public void addComment(String title, Blog blog) {
        update(title, blog);
    }

    public void likeBlog(String title, Blog blog) {
        update(title, blog);
    }

    public void update(String title, Blog blog){
        blogMap.remove(title);
        blogMap.put(title, blog);
    }

    public List<Blog> findAllByBlogStatus(BlogStatus blogStatus) {
        return blogMap.values().stream()
                .filter(blog -> blog.getBlogStatus().equals(blogStatus))
                .toList();
    }

    public Integer count() {
        return blogMap.size();
    }

    public Integer countByTitleContains(String keyword) {
        return blogMap.values().stream()
                .filter(blog-> blog.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList()
                .size();
    }

    public List<Blog> findByUserEmail(String email) {
        return blogMap.values().stream()
                .filter(blog -> blog.getUser().getEmail().equals(email))
                .toList();
    }

    public void delete(String title) {
        blogMap.remove(title);
    }
}
