package com.springboot.cli.service;

import com.springboot.cli.model.DO.ExerciseRecordDO;

import java.util.List;

public interface ExerciseRecordService {
    Integer hasDoneExercise(String studentId, Long exerciseId);

    List<ExerciseRecordDO> getExerciseRecord(String studentId, Long questionId);

    ExerciseRecordDO getExerciseRecord(Long recordId);
}
