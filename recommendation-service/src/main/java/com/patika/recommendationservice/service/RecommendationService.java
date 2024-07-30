package com.patika.recommendationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.patika.recommendationservice.converter.RecommendationConverter;
import com.patika.recommendationservice.dto.request.RecommendationRequest;
import com.patika.recommendationservice.dto.request.RecommendationRequestList;
import com.patika.recommendationservice.dto.response.OpenAiErrorResponse;
import com.patika.recommendationservice.dto.response.OpenAiSuccessResponse;
import com.patika.recommendationservice.dto.response.RecommendationResponse;
import com.patika.recommendationservice.dto.response.RecommendationResponseList;
import com.patika.recommendationservice.exception.RecommendationException;
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


    public RecommendationResponse getRecommendation2(RecommendationRequest recommendationRequest) {
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

            log.info("Open ai response: {}", response);

            OpenAiSuccessResponse openAiSuccessResponse = new OpenAiSuccessResponse(response);
            return RecommendationConverter.toSuccessResponse(openAiSuccessResponse);

        } catch (IOException e) {
            log.error("Open ai istek atılırken hata olustu: {}", e.getMessage());
            RecommendationResponse recommendationResponse = RecommendationResponse.builder()
                    .error(new OpenAiErrorResponse(e.getMessage(), "exception", null, "exception"))
                    .build();

            return RecommendationConverter.toFailedResponse(recommendationResponse);
        }

    }

    public RecommendationResponseList getRecommendation(RecommendationRequestList request) {
        StringBuilder meeting = new StringBuilder("Below is a list of the articles in my library: \n");
        int i = 1;
        for (String blog : request.getRepository()) {
            meeting.append(i).append(")").append(blog).append("\n");
        }

        meeting.append("\n");
        meeting.append("Below is a list of articles I have already read: \n");
        for (String blog : request.getHistory()){
            meeting.append(i).append(")").append(blog).append("\n");
        }
        meeting.append("\n");
        meeting.append("Considering the articles I have already read, can you give me 3 suggestions to read from the library I have given you? \n");
        meeting.append("""
                I want you to give me the data in json format. in a list called blogTitles.
                {
                    blogTitles : [
                        “blog title”,
                        “blog title”,
                        “blog title”,
                       ]
                }
                """);

        String response = openAiChatModel.call(meeting.toString());

        try {
            return objectMapper.readValue(response, RecommendationResponseList.class);
        }catch (IOException e){
            log.error("Json parse error: {}", e.getMessage());
            throw new RecommendationException("Json parse error");
        }
    }
}
