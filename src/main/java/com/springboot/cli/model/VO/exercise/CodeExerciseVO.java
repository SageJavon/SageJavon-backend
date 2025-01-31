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
public class CodeExerciseVO {
    private Long id;
    private String questionText;
    private List<KnowledgeVO> knowledgeConcept;
    private Integer difficulty;
    private Integer done;
    private Integer review;
    private String chapter;

    public CodeExerciseVO(ExerciseDO codeExercise, List<KnowledgeVO> knowledgeList, Integer done, Integer review) {
        this.id = codeExercise.getId();
        this.questionText = codeExercise.getQuestionText();
        this.knowledgeConcept = knowledgeList;
        this.difficulty = codeExercise.getDifficulty();
        this.done = done;
        this.review = review;
        this.chapter = codeExercise.getChapter();
    }
}
