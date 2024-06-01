package com.springboot.cli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cli.model.DO.ExerciseKnowledgeDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExerciseKnowledgeMapper extends BaseMapper<ExerciseKnowledgeDO> {
}
