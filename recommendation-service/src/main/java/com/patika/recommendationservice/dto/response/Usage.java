package com.patika.recommendationservice.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usage {

    private int prompt_tokens;

    private int completion_tokens;

    private int total_tokens;

}
