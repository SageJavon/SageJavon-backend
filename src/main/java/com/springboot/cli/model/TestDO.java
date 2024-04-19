package com.springboot.cli.model;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
@Data
@TableName("test")
public class TestDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String text;
}
