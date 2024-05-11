package com.springboot.cli.service;

import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeVO;
import com.springboot.cli.repository.KnowledgeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class KnowledgeServiceImpl implements KnowledgeService{

    @Resource
    KnowledgeRepository knowledgeRepository;

    @Override
    public void save(List<KnowledgeDO> modelList) {
        modelList.forEach(model -> knowledgeRepository.saveOrUpdate(model));
    }

    @Override
    public List<KnowledgeVO> get(Long studentId) {
        if(studentId == null) throw new OpException(OpExceptionEnum.USER_ID_EMPTY);
        List<KnowledgeVO> knowledgeGraph = knowledgeRepository.getknowledgeGraphVO(studentId);
        knowledgeGraph.forEach(knowledge -> {
            if(knowledge.getQuery() == null) knowledge.setQuery(0);
            if(knowledge.getLevel() == null) knowledge.setLevel(0);
        });
        return knowledgeGraph;
    }
}
