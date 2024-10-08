package com.patika.bloghubservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationRequestList {
    private List<String> repository;
    private List<String> history;
}
