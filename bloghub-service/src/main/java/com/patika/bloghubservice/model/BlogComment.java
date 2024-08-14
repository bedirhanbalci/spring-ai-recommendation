package com.patika.bloghubservice.model;

import com.patika.bloghubservice.model.enums.BlogCommentType;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BlogComment {

    private User user;
    private String comment;
    private LocalDateTime createdDate;
    private BlogCommentType blogCommentType;

    public BlogComment(User user, String comment) {
        this.user = user;
        this.comment = comment;
        this.createdDate = LocalDateTime.now();
        this.blogCommentType = BlogCommentType.INITIAL;
    }
}
