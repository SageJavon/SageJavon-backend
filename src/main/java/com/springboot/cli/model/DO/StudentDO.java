package com.springboot.cli.model.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("student")
public class StudentDO {
    private String id;
    private String nickname;
    private String portrait;
    private Integer gender;
}
