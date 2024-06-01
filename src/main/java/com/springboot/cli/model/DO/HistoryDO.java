package com.springboot.cli.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("history")
public class HistoryDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long chatId;
    private Integer role;
    private String content;
    private LocalDateTime timeStamp;
    //排序记号
    private Integer sort;
}
