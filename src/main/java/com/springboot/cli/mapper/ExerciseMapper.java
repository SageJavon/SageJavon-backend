package com.springboot.cli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cli.model.DO.ExerciseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExerciseMapper extends BaseMapper<ExerciseDO> {
    @Select("select count(*) from exercise where type=#{type}")
    Integer getExerciseNumber(int type);
}
