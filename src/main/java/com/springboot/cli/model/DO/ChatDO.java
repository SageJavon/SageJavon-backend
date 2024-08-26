package com.springboot.cli.model.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat")
public class ChatDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String studentId;
    private String title;
    //历史聊天记录数量
    private Integer sort;
    //是否长期保存
    private Integer longTerm;
    private LocalDateTime updateTime;
    //逻辑删除标识
    private Integer deletedFlag;
}
