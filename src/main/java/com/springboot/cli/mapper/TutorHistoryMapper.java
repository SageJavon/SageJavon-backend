package com.springboot.cli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cli.model.DO.TutorHistoryDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TutorHistoryMapper extends BaseMapper<TutorHistoryDO> {
}
