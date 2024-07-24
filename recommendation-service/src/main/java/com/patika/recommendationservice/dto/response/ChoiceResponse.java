package com.patika.recommendationservice.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceResponse {

    private int index;

    private MessageResponse message;

    private Object logprobs;

    private String finish_reason;

}
