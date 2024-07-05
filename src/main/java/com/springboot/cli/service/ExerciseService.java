package com.springboot.cli.service;

import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.VO.exercise.FeedBackVO;

import java.util.List;

public interface ExerciseService {
    List<ExerciseDO> getExerciseList(int type);

    ExerciseDO getExerciseDetail(Long id);

    FeedBackVO getFeedBack(Long id, String answer, Integer submitNum);

    FeedBackVO getSelectFeedBack(Long id, String choice);
}
