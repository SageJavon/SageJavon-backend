package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.ExerciseMapper;
import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.repository.IExerciseRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExerciseRepository extends ServiceImpl<ExerciseMapper, ExerciseDO> implements IExerciseRepo {
    @Resource
    ExerciseMapper exerciseMapper;

    @Override
    public Integer getExerciseNumber(int type) {
        return exerciseMapper.getExerciseNumber(type);
    }

    @Override
    public List<ExerciseDO> getRecList(Integer questionNum) {
        return exerciseMapper.getRecList(questionNum);
    }


}
