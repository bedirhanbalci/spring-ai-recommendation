package com.patika.recommendationservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationRequestList {
    private List<String> repository;
    private List<String> history;
}
