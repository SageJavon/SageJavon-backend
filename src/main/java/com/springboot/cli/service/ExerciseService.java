package com.springboot.cli.service;

import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.VO.exercise.ExercisePage;
import com.springboot.cli.model.VO.exercise.FeedBackVO;

import java.util.List;

public interface ExerciseService {
    ExerciseDO getExerciseById(Long id);

    FeedBackVO getFeedBack(Long id, String answer, Integer submitNum);

    FeedBackVO getSelectFeedBack(Long id, String choice);

    Integer getExerciseLNumber(int type);

    List<ExerciseDO> getRecList(Integer questionNum);

    ExercisePage page(Integer type, Integer pageNum, Integer pageSize, Integer difficulty, List<Long> knowledgeId, Integer difficultyOrder);
}
