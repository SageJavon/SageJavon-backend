package com.springboot.cli.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("chat")
public class ChatDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String title;
    //历史聊天记录数量
    private Integer sort;
    //是否长期保存
    private Integer longTerm;
    private LocalDateTime updateTime;
}
