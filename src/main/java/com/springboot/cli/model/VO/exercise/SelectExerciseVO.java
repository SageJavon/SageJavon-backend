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
public class SelectExerciseVO {
    private Long id;
    private String questionText;
    private List<KnowledgeVO> knowledgeConcept;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private Integer difficulty;

    public SelectExerciseVO(ExerciseDO selectExercise, List<KnowledgeVO> knowledgeList) {
        this.id = selectExercise.getId();
        this.questionText = selectExercise.getQuestionText();
        this.knowledgeConcept = knowledgeList;
        this.choiceA = selectExercise.getChoiceA();
        this.choiceB = selectExercise.getChoiceB();
        this.choiceC = selectExercise.getChoiceC();
        this.choiceD = selectExercise.getChoiceD();
        this.difficulty = selectExercise.getDifficulty();
    }
}
