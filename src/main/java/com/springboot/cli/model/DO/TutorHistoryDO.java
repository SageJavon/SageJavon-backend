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
@TableName("tutor_history")
public class TutorHistoryDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String studentId;
    private Integer role;
    private String content;
    private LocalDateTime timeStamp;
}
