package com.springboot.cli.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("exercise_knowledge")
public class ExerciseKnowledgeDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long knowledgeId;
    private Long exerciseId;
}
