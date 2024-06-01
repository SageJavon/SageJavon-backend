package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.ExerciseRecordMapper;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import com.springboot.cli.repository.IExerciseRecordRepo;
import org.springframework.stereotype.Service;

@Service
public class ExerciseRecordRepository extends ServiceImpl<ExerciseRecordMapper, ExerciseRecordDO> implements IExerciseRecordRepo {
}
