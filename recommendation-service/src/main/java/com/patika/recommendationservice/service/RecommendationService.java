package com.patika.recommendationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.recommendationservice.converter.RecommendationConverter;
import com.patika.recommendationservice.dto.request.RecommendationRequest;
import com.patika.recommendationservice.dto.response.OpenAiErrorResponse;
import com.patika.recommendationservice.dto.response.RecommendationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final HttpClient httpClient;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    public RecommendationResponse getRecommendation(RecommendationRequest recommendationRequest) {
        var body = """
                    {
                        "model": "gpt-3.5-turbo",
                        "messages": [
                                        {
                                            "role": "system", "content": "You are a helpful assistant.",
                                            "role": "user", "content": "What is a LLM?"
                                        }
                                    ]
                    }
                """;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header( "Content-Type", "application/json")
                .header( "Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info(response.body());
            RecommendationResponse recommendationResponse = new ObjectMapper().readValue(response.body(), RecommendationResponse.class);
            if (response.statusCode() != 200) {
                return RecommendationConverter.toFailedResponse(recommendationResponse);
            } else {
                return RecommendationConverter.toSuccessResponse(recommendationResponse);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            /**
             * Exception Handler olmalı, bu şekilde değil
             */
            RecommendationResponse recommendationResponse = new RecommendationResponse();
            recommendationResponse.setError(new OpenAiErrorResponse(e.getMessage(), "exception", null, "exception"));
            return RecommendationConverter.toFailedResponse(recommendationResponse);
        }

    }

}
