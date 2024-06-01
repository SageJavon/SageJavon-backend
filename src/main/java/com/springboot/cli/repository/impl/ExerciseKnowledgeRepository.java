package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.ExerciseKnowledgeMapper;
import com.springboot.cli.model.DO.ExerciseKnowledgeDO;
import com.springboot.cli.repository.IExerciseKnowledgeRepo;
import org.springframework.stereotype.Service;

@Service
public class ExerciseKnowledgeRepository extends ServiceImpl<ExerciseKnowledgeMapper, ExerciseKnowledgeDO> implements IExerciseKnowledgeRepo {
}
