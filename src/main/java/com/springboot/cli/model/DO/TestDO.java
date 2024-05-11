package com.springboot.cli.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Builder;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@Builder
@TableName("test")
public class TestDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String text;
}

