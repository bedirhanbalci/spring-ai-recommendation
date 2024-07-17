package com.patika.recommendationservice.service;

import com.patika.recommendationservice.dto.request.RecommendationRequest;
import com.patika.recommendationservice.dto.response.RecommendationResponse;
import com.patika.recommendationservice.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final HttpClient httpClient;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    public RecommendationResponse getRecommendation(RecommendationRequest recommendationRequest) {
        var body = """
                {
                    "model": "gpt4",
                    "messages": [
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }
                """.formatted(recommendationRequest.getMessage());

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header( "Content-Type", "application/json")
                .header( "Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return new RecommendationResponse(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new RecommendationResponse("Error occured.");
        }

    }

    private RecommendationResponse parseResponse(String responseBody) {
        // Extract the actual response content from the JSON response
        String content = JsonUtil.extractContent(responseBody);
        return new RecommendationResponse(content);
    }

}
