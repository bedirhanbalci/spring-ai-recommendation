package com.patika.bloghubservice.controller;

import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.request.LikeBlogRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.dto.response.BlogStatistic;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.dto.response.UserBlogStatistic;
import com.patika.bloghubservice.model.enums.BlogStatus;
import com.patika.bloghubservice.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogController implements Controller {

    private final BlogService blogService;

    @PostMapping
    public GenericResponse<BlogResponse> createBlog(@RequestBody BlogSaveRequest request) {
        return GenericResponse.success(blogService.createBlog(request), HttpStatus.CREATED, "Blog created.");
    }

    @GetMapping
    public GenericResponse<List<BlogResponse>> getAllBlogs(@RequestParam int size, @RequestParam int page) {
        return GenericResponse.success(blogService.getAllBLogs(size, page), HttpStatus.OK);
    }

    @GetMapping("/by-title")
    public BlogResponse getBlogByTitle(@RequestParam String title) {
        return blogService.getBlogByTitle(title);
    }

    // user'ın like attığı bloglar history olarak alınır, buna göre mevcut bloglar içerisinden 3 blog önerisi yapar.
    @GetMapping("/recommendation")
    public GenericResponse<List<BlogResponse>> getBlogRecommendations(@RequestParam String email) {
        return GenericResponse.success(blogService.getBlogRecommendations(email), HttpStatus.OK, "Blog recommendations listed.");
    }

    @PutMapping("/like-blog")
    public void likeBlog(@RequestBody LikeBlogRequest likeBlogRequest) {
        blogService.likeBlog(likeBlogRequest);
    }


    @PutMapping("/{title}/users/{email}")
    public void addComment(@PathVariable String title, @PathVariable String email, @RequestBody String comment) {
        blogService.addComment(title, email, comment);
    }

    @PutMapping("/change-status/{title}")
    public void chageBlogStatus(@PathVariable String title, @RequestBody BlogStatus blogStatus) {
        blogService.changeBlogStatus(blogStatus, title);
    }

    @GetMapping("/{title}/like-count")
    public Long getLikeCountByTitle(@PathVariable String title) {
        return blogService.getLikeCountByTitle(title);
    }

    // bloglarla ilgili istatistikler
    @GetMapping("/blog-count")
    public Integer getTotalBlogCount() {
        return blogService.getTotalBlogCount();
    }

    @GetMapping("/blogs-count-by-status/{blogStatus}")
    public Integer getBlogCountByStatus(@PathVariable BlogStatus blogStatus) {
        return blogService.getBlogCountByStatus(blogStatus);
    }

    @GetMapping("/blogs-statistics/users/{email}")
    public GenericResponse<UserBlogStatistic> getBlogStatisticsByUser(@PathVariable String email) {
        return GenericResponse.success(blogService.getBlogStatisticsByUser(email), HttpStatus.OK);
    }

    @GetMapping("/blogs-statistics/{title}")
    public GenericResponse<BlogStatistic> getBlogStatisticsByTitle(@PathVariable String title) {
        return GenericResponse.success(blogService.getBlogStatisticsByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/read/{title}")
    public void readBlog(@PathVariable String title) {
        blogService.readBlog(title);
    }

    @GetMapping("/blogs-count/{keyword}")
    public Integer getBlogCountByKeyword(@PathVariable String keyword) {
        return blogService.getBlogCountByKeyword(keyword);
    }

    // 8. soru c şıkkı : • Kullanıcı kendi PUBLISHED ve DRAFT olan blog’larını getiren endpoint
    @GetMapping("/users/{email}")
    public GenericResponse<List<BlogResponse>> getAllDraftOrPublishedBlogsByEmail(@PathVariable String email) {
        return GenericResponse.success(blogService.getAllDraftOrPublishedBlogsByEmail(email), HttpStatus.OK);
    }

    // 8. soru d şıkkı : • Kullanıcı kendi blog’larından DRAFT statüsünde olanları hard delete ile silebilir.
    @DeleteMapping("/{title}/users/{email}")
    public GenericResponse<String> hardDeleteBlogIsDraft(@PathVariable String title, @PathVariable String email) {
        return GenericResponse.success(blogService.hardDeleteBlogIsDraft(title, email), HttpStatus.OK);
    }
}
