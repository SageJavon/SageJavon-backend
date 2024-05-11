package com.springboot.cli.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("student")
public class StudentDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String nickname;
    private String portrait;
    private Integer gender;
}
