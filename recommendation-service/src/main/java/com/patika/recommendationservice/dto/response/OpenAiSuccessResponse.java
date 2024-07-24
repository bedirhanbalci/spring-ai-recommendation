package com.patika.recommendationservice.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAiSuccessResponse {

//    private String id;
//
//    private String object;
//
//    private int created;
//
//    private String model;
//
//    private List<ChoiceResponse> choices;
//
//    private Usage usage;
//
//    private String system_fingerprint;

    private String message;

}
