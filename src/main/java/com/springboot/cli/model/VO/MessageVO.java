package com.springboot.cli.model.VO;

import com.springboot.cli.model.DO.HistoryDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageVO {
    private String content;
    private Integer role;
    private LocalDateTime timeStamp;

    public MessageVO(HistoryDO history) {
        this.content = history.getContent();
        this.role = history.getRole();
        this.timeStamp = history.getTimeStamp();
    }
}
