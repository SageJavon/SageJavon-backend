package com.springboot.cli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cli.model.DO.ExerciseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExerciseMapper extends BaseMapper<ExerciseDO> {
    @Select("select * from exercise where type=#{type} order by rand() limit 10")
    List<ExerciseDO> getExerciseList(int type);

    @Select("select count(*) from exercise where type=#{type}")
    Integer getExerciseNumber(int type);
}
