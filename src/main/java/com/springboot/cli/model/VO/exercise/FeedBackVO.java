package com.springboot.cli.model.VO.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackVO {
    private Integer score;
    private String correctAnswer;
    private String suggestion;
}
