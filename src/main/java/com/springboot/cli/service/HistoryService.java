package com.springboot.cli.service;

import com.springboot.cli.model.VO.MessageVO;
import com.springboot.cli.model.VO.SMessageVO;

import java.util.List;

public interface HistoryService {
    List<MessageVO> buildMessage(Long chatId, List<SMessageVO> messageList);

    List<MessageVO> getHistory(Long chatId);
}
