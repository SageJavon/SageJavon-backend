package com.springboot.cli.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeVO;

import java.util.List;

public interface IKnowledgeRepo extends IService<KnowledgeDO> {
    List<KnowledgeVO> getknowledgeGraphVO(Long studentId);
}
