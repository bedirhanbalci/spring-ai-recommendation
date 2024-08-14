package com.patika.bloghubservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "kullanıcı bulunamadı";
    public static final String USER_ALREADY_DEFINED = "bu email ile kayıtlı kullanıcı bulundu";
    public static final String USER_EMAIL_CAN_NOT_BE_EMPTY = "email alanı boş olamaz";
    public static final String PASSWORD_INCORRECT = "Parola yanlış";
    public static final String VALIDATION = "Validation exception";
    public static final String LIKE_COUNT_EXCEPTION = "Bir bloğa en fazla 50 like atabilirsiniz";
    public static final String CHANGE_BLOG_STATUS_EXCEPTION = "statüsü PUBLISHED olan bir blog silinemez.";
    public static final String USER_STATUS_EXCEPTION = "Blog paylaşabilmek için APPROVED statüsünde bir kullanıcı olmanız gerekir";
    public static final String BLOG_NOT_FOUND_EXCEPTION = "Blog bulunamadı.";
    public static final String USER_STATUS_NOT_APPROVED = "APPROVED statüde olmayan kullanıcılar PREMIUM pakete geçemez.";
    public static final String RECOMMENDATION_EXCEPTION = "You need to like to get a blog recommendation.";


}
