package com.springboot.cli.service;

import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeGraphVO;
import com.springboot.cli.model.VO.exercise.FeedBackVO;
import com.springboot.cli.model.VO.exercise.KnowledgeVO;

import java.util.List;

public interface KnowledgeService {
    void save(List<KnowledgeDO> modelList);

    List<KnowledgeGraphVO> get(String studentId);

    List<KnowledgeVO> getKnowledgeList(Long exerciseId);

    List<KnowledgeDO> getKnowledgeList();
}
