package com.springboot.cli.model.VO.exercise;

import com.springboot.cli.model.DO.ExerciseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseVO {
    private Long id;
    private String questionText;
    private List<KnowledgeVO> knowledgeConcept;
    private Integer difficulty;
    private Integer done;
    private Integer type;

    public ExerciseVO(ExerciseDO exercise, List<KnowledgeVO> knowledgeList, Integer done) {
        this.id = exercise.getId();
        this.questionText = exercise.getQuestionText();
        this.knowledgeConcept = knowledgeList;
        this.difficulty = exercise.getDifficulty();
        this.done = done;
        this.type = exercise.getType();
    }
}
