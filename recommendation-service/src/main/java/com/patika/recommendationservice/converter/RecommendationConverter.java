package com.patika.recommendationservice.converter;

import com.patika.recommendationservice.dto.response.RecommendationResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecommendationConverter {

    public static RecommendationResponse toFailedResponse(RecommendationResponse recommendationResponse) {

        return RecommendationResponse.builder()
                .error(recommendationResponse.getError())
                .build();
    }

    public static RecommendationResponse toSuccessResponse(RecommendationResponse recommendationResponse) {

        return RecommendationResponse.builder()
                .openAiSuccessResponse(recommendationResponse.getOpenAiSuccessResponse())
                .build();
    }
}
