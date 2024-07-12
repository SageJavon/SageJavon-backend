package com.springboot.cli.model.VO.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseVOPage {
    private List<ExerciseVO> exerciseList;
    private Long total;
    private Long pages;
}
