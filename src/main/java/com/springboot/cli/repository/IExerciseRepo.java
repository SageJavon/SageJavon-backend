package com.springboot.cli.repository;


import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.cli.model.DO.ExerciseDO;

import java.util.List;

public interface IExerciseRepo extends IService<ExerciseDO> {
    Integer getExerciseNumber(int type);

    List<ExerciseDO> getRecList(Integer questionNum);
}
