package com.springboot.cli.model.VO;

import com.springboot.cli.model.VO.exercise.CodeExerciseVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllInformationVO {
    private Integer selectNumber;
    private Integer codeNumber;
    private LocalDateTime latestTime;
    private List<CodeExerciseVO> popularQuestion;
    private Integer solveDays;
    private Integer solveQuestions;
    private Map<LocalDate, Integer> solveNumbersPerDay;
}
