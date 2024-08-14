package com.patika.recommendationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.recommendationservice.dto.request.RecommendationRequestList;
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
