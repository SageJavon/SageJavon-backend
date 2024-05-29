package com.springboot.cli.service;

import com.springboot.cli.model.VO.ChatVO;

import java.util.List;

public interface ChatService {
    Long insert();

    List<ChatVO> getList();

    Void delete(Long chatId);
}
