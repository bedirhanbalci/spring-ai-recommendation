package com.patika.recommendationservice.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OpenAiSuccessResponse {

    private String question;
    private String answer;


}
