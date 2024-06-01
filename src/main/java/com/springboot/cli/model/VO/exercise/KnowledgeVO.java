package com.springboot.cli.model.VO.exercise;

import com.springboot.cli.model.DO.KnowledgeDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KnowledgeVO {
    private Long knowledgeId;
    private String knowledge;

    public KnowledgeVO(KnowledgeDO knowledgeDO) {
        this.knowledgeId = knowledgeDO.getId();
        this.knowledge = knowledgeDO.getKnowledge();
    }
}
