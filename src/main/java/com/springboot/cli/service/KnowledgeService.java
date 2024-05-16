package com.springboot.cli.service;

import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeVO;

import java.util.List;

public interface KnowledgeService {
    void save(List<KnowledgeDO> modelList);

    List<KnowledgeVO> get(String studentId);
}
