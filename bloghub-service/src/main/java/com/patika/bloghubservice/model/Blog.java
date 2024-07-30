package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.enums.BlogStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Blog {
    private String title;
    private String text;
    private LocalDateTime createdDate;
    private User user;
    private BlogStatus blogStatus;
    private HashMap<User, Integer> userLikeCounts;
    private Long readingCount;
    private List<BlogComment> blogCommentList;

    public Blog(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.blogStatus = BlogStatus.DRAFT;
        this.blogCommentList = new ArrayList<>();
        this.userLikeCounts = new HashMap<>();
        this.readingCount = 0L;
    }
}
