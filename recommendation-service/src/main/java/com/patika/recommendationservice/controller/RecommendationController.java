package com.patika.recommendationservice.controller;

import com.patika.recommendationservice.dto.request.RecommendationRequest;
import com.patika.recommendationservice.dto.response.GenericResponse;
import com.patika.recommendationservice.dto.response.RecommendationResponse;
import com.patika.recommendationservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping()
    public GenericResponse<RecommendationResponse> recommendations(@RequestBody RecommendationRequest recommendationRequest) {
        return GenericResponse.success(recommendationService.getRecommendation(recommendationRequest), HttpStatus.OK);
    }

}
