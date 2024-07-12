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
public class ExerciseRecordVO {
    private Long recordId;
    private Long exerciseId;
    private String questionText;
    private List<KnowledgeVO> knowledgeConcept;
    private Integer score;
    private LocalDateTime submitTime;
    private Integer difficulty;
    private Integer type;

    public ExerciseRecordVO(ExerciseDO exercise, List<KnowledgeVO> knowledgeConcept, ExerciseRecordDO exerciseRecord) {
        this.recordId = exerciseRecord.getId();
        this.exerciseId = exercise.getId();
        this.questionText = exercise.getQuestionText();
        this.knowledgeConcept = knowledgeConcept;
        this.score = (exercise.getType() == 0) ? (int) (exerciseRecord.getScore() * 100) : (int) exerciseRecord.getScore().doubleValue();
        this.submitTime = exerciseRecord.getSubmitTime();
        this.difficulty = exercise.getDifficulty();
        this.type = exercise.getType();
    }
}
