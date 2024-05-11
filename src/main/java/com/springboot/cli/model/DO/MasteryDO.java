package com.springboot.cli.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("mastery")
public class MasteryDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long knowledgeId;
    private Integer level;
    private Integer query;
}
