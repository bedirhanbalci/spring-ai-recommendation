package com.patika.bloghubservice.client;

import com.patika.bloghubservice.client.dto.RecommendationRequestList;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.client.dto.RecommendationResponseList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "recommendation-service", url = "http://localhost:8081/api/v1/recommendations")
public interface RecommendationService {

    @PostMapping("/recommendations")
    GenericResponse<RecommendationResponseList> recommendations(@RequestBody RecommendationRequestList recommendationRequest);
}
