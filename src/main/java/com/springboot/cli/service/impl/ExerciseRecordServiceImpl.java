package com.springboot.cli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import com.springboot.cli.repository.impl.ExerciseRecordRepository;
import com.springboot.cli.service.ExerciseRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseRecordServiceImpl implements ExerciseRecordService {
    @Resource
    private ExerciseRecordRepository exerciseRecordRepository;

    @Override
    public Integer hasDoneExercise(String studentId, Long exerciseId) {
        LambdaQueryWrapper<ExerciseRecordDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExerciseRecordDO::getStudentId, studentId);
        queryWrapper.eq(ExerciseRecordDO::getExerciseId, exerciseId);
        ExerciseRecordDO exerciseRecord = exerciseRecordRepository.getOne(queryWrapper);
        return exerciseRecord == null ? 0 : 1;
    }

    @Override
    public List<ExerciseRecordDO> getExerciseRecord(String studentId, Long questionId) {
        LambdaQueryWrapper<ExerciseRecordDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExerciseRecordDO::getExerciseId, questionId);
        queryWrapper.eq(ExerciseRecordDO::getStudentId, studentId);
        return exerciseRecordRepository.list(queryWrapper);
    }

    @Override
    public ExerciseRecordDO getExerciseRecord(Long recordId) {
        return exerciseRecordRepository.getById(recordId);
    }

    @Override
    public List<ExerciseRecordDO> getStudentAllExerciseRecord(String studentId) {
        LambdaQueryWrapper<ExerciseRecordDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExerciseRecordDO::getStudentId, studentId);
        return exerciseRecordRepository.list(queryWrapper);
    }

    @Override
    public int getExerciseRecordsByExerciseID(Long exerciseID) {
        LambdaQueryWrapper<ExerciseRecordDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExerciseRecordDO::getExerciseId, exerciseID);
        List<ExerciseRecordDO> records = exerciseRecordRepository.list(queryWrapper);
        List<String> distinctStudentIds = records.stream()
                .map(ExerciseRecordDO::getStudentId)
                .distinct()
                .collect(Collectors.toList());
        return distinctStudentIds.size();
    }
}
