package com.springboot.cli.model.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KnowledgeVO {
    private Long id;
    private Long parentId;
    private Integer level;
    private Integer query;
    private String name;
}
