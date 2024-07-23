package com.patika.recommendationservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {
    /**
     * "message": "You exceeded your current quota, please check your plan and billing details. For more information on this error, read the docs: https://platform.openai.com/docs/guides/error-codes/api-errors.",
     * "type": "insufficient_quota",
     * "param": null,
     * "code": "insufficient_quota"
     */
    OpenAiErrorResponse error;

    /**
     *
     */
    OpenAiSuccessResponse openAiSuccessResponse; // henüz success response göremedik

}
