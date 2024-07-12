package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.KnowledgeMapper;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeGraphVO;
import com.springboot.cli.model.VO.exercise.KnowledgeVO;
import com.springboot.cli.repository.IKnowledgeRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class KnowledgeRepository extends ServiceImpl<KnowledgeMapper, KnowledgeDO> implements IKnowledgeRepo {

    @Resource
    KnowledgeMapper knowledgeMapper;

    @Override
    public List<KnowledgeGraphVO> getKnowledgeGraphVO(String studentId) {
        return knowledgeMapper.getKnowledgeGraphVO(studentId);
    }

    @Override
    public List<KnowledgeVO> getKnowledgeList(Long exerciseId) {
        return knowledgeMapper.getKnowledgeList(exerciseId);
    }
}
