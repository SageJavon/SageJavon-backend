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
public class DetailSelectExerciseRecordVO {
    private Long questionId;
    private String questionText;
    private List<KnowledgeVO> knowledgeConcept;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String answer;
    private String correctAnswer;
    private Integer score;
    private LocalDateTime submitTime;
    private Integer difficulty;

    public DetailSelectExerciseRecordVO(ExerciseDO exercise, List<KnowledgeVO> knowledgeConcept, ExerciseRecordDO exerciseRecord) {
        this.questionId = exercise.getId();
        this.questionText = exercise.getQuestionText();
        this.knowledgeConcept = knowledgeConcept;
        this.choiceA = exercise.getChoiceA();
        this.choiceB = exercise.getChoiceB();
        this.choiceC = exercise.getChoiceC();
        this.choiceD = exercise.getChoiceD();
        this.score = (int) (double) exerciseRecord.getScore();
        this.correctAnswer = exercise.getCorrectAnswer();
        this.answer = exerciseRecord.getAnswer();
        this.submitTime = exerciseRecord.getSubmitTime();
        this.difficulty = exercise.getDifficulty();
    }
}
