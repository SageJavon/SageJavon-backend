package com.springboot.cli.model.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KnowledgeVO {
    private Long id;
    private Long parentId;
    private Integer level;
    private Integer query;
    private String name;
}
