package com.patika.recommendationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.patika.recommendationservice.converter.RecommendationConverter;
import com.patika.recommendationservice.dto.request.RecommendationRequest;
import com.patika.recommendationservice.dto.response.OpenAiErrorResponse;
import com.patika.recommendationservice.dto.response.OpenAiSuccessResponse;
import com.patika.recommendationservice.dto.response.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final OpenAiChatModel openAiChatModel;

    private final ObjectMapper objectMapper;


    public RecommendationResponse getRecommendation(RecommendationRequest recommendationRequest) {
        try {

            String message = recommendationRequest.getMessage();

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("model", "gpt-3.5-turbo");

            ArrayNode messagesArray = rootNode.putArray("messages");
            ObjectNode systemMessage = messagesArray.addObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant.");

            ObjectNode userMessage = messagesArray.addObject();
            userMessage.put("role", "user");
            userMessage.put("content", message);

            String requestBody = objectMapper.writeValueAsString(rootNode);

            String response = openAiChatModel.call(requestBody);

            log.info(response);

            OpenAiSuccessResponse openAiSuccessResponse = new OpenAiSuccessResponse(response);
            return RecommendationConverter.toSuccessResponse(openAiSuccessResponse);

        } catch (IOException e) {
            e.printStackTrace();
            RecommendationResponse recommendationResponse = new RecommendationResponse();
            recommendationResponse.setError(new OpenAiErrorResponse(e.getMessage(), "exception", null, "exception"));
            return RecommendationConverter.toFailedResponse(recommendationResponse);
        }

    }

}
