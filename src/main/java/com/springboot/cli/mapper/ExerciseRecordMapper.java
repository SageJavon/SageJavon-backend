package com.springboot.cli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExerciseRecordMapper extends BaseMapper<ExerciseRecordDO> {
}
