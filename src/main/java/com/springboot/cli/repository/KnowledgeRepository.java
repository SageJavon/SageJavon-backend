package com.springboot.cli.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.KnowledgeMapper;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class KnowledgeRepository extends ServiceImpl<KnowledgeMapper, KnowledgeDO> implements IKnowledgeRepo {

    @Resource
    KnowledgeMapper knowledgeMapper;

    @Override
    public List<KnowledgeVO> getknowledgeGraphVO(Long studentId) {
        return knowledgeMapper.getKnowledgeGraphVO(studentId);
    }
}
