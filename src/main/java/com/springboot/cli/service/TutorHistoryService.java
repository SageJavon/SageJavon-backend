package com.springboot.cli.service;

import com.springboot.cli.model.VO.SMessageVO;

import java.util.List;

public interface TutorHistoryService {
    Void buildMessage(List<SMessageVO> sMessageList);
}
