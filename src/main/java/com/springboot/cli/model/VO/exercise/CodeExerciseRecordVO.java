package com.springboot.cli.model.VO.exercise;

import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeExerciseRecordVO {
    private Long recordId;
    private Long exerciseId;
    private String questionText;
    private List<KnowledgeVO> knowledgeConcept;
    private Integer score;
    private LocalDateTime submitTime;
    private Integer difficulty;

    public CodeExerciseRecordVO(ExerciseDO exercise, List<KnowledgeVO> knowledgeConcept, ExerciseRecordDO exerciseRecord) {
        this.recordId = exerciseRecord.getId();
        this.exerciseId = exerciseRecord.getId();
        this.questionText = exercise.getQuestionText();
        this.knowledgeConcept = knowledgeConcept;
        this.score = (int) (exerciseRecord.getScore() * 100);
        this.submitTime = exerciseRecord.getSubmitTime();
        this.difficulty = exercise.getDifficulty();
    }
}
