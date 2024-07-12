package com.springboot.cli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeGraphVO;
import com.springboot.cli.model.VO.exercise.KnowledgeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgeMapper extends BaseMapper<KnowledgeDO> {

    @Select("with stu_mas as (select * from mastery where student_id=#{studentId})" +
            "select k.id, k.parent_id, m.level, m.query, k.name " +
            "from knowledge k left join stu_mas m " +
            "on m.knowledge_id=k.id")
    List<KnowledgeGraphVO> getKnowledgeGraphVO(String studentId);

    @Select("SELECT k.id AS knowledge_id, k.knowledge AS knowledge FROM knowledge k JOIN exercise_knowledge ek ON k.id = ek.knowledge_id WHERE ek.exercise_id = #{exerciseId}")
    List<KnowledgeVO> getKnowledgeList(Long exerciseId);
}
