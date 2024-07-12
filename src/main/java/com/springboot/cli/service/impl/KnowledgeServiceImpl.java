package com.springboot.cli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.DO.ExerciseKnowledgeDO;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeGraphVO;
import com.springboot.cli.model.VO.exercise.FeedBackVO;
import com.springboot.cli.model.VO.exercise.KnowledgeVO;
import com.springboot.cli.repository.impl.ExerciseKnowledgeRepository;
import com.springboot.cli.repository.impl.ExerciseRecordRepository;
import com.springboot.cli.repository.impl.KnowledgeRepository;
import com.springboot.cli.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.springboot.cli.common.CommonConstants.submitNumThreshold;

@Service
@Slf4j
public class KnowledgeServiceImpl implements KnowledgeService {
    @Resource
    KnowledgeRepository knowledgeRepository;

    @Resource
    ExerciseKnowledgeRepository exerciseKnowledgeRepository;

    @Override
    public void save(List<KnowledgeDO> modelList) {
        modelList.forEach(model -> knowledgeRepository.saveOrUpdate(model));
    }

    @Override
    public List<KnowledgeGraphVO> get(String studentId) {
        if(studentId == null) throw new OpException(OpExceptionEnum.USER_ID_EMPTY);
        List<KnowledgeGraphVO> knowledgeGraph = knowledgeRepository.getKnowledgeGraphVO(studentId);
        if(knowledgeGraph != null)
            knowledgeGraph.forEach(knowledge -> {
                if(knowledge.getQuery() == null) knowledge.setQuery(0);
                if(knowledge.getLevel() == null) knowledge.setLevel(0);
            });
        return knowledgeGraph;
    }

    @Override
    public List<KnowledgeVO> getKnowledgeList(Long exerciseId) {
        return knowledgeRepository.getKnowledgeList(exerciseId);
    }

    @Override
    public List<KnowledgeDO> getKnowledgeList() {
        return knowledgeRepository.list();
    }
}
