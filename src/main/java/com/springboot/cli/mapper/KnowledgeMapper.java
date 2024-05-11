package com.springboot.cli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgeMapper extends BaseMapper<KnowledgeDO> {

    @Select("with stu_mas as (select * from mastery where student_id=#{studentId})" +
            "select k.id, k.parent_id, m.level, m.query, k.name " +
            "from knowledge k left join stu_mas m " +
            "on m.knowledge_id=k.id")
    List<KnowledgeVO> getKnowledgeGraphVO(Long studentId);
}
