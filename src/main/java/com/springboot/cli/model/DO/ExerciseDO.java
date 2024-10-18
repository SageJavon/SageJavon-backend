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
@TableName("exercise")
public class ExerciseDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String questionText;
    private String correctAnswer;
    private Integer difficulty;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private Integer type;
    private String chapter;
}
