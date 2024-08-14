package com.patika.bloghubservice.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SocialMedia {
    private String name;
    private String url;
}
