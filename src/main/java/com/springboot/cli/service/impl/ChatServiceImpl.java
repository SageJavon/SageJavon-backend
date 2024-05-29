package com.springboot.cli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.ChatDO;
import com.springboot.cli.model.VO.ChatVO;
import com.springboot.cli.repository.impl.ChatRepository;
import com.springboot.cli.service.ChatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    ChatRepository chatRepository;

    @Override
    public Long insert() {
        String studentId = AuthStorage.getUser().getUserId();
        ChatDO chat = ChatDO.builder()
                .studentId(studentId)
                .sort(0)
                .longTerm(0)
                .updateTime(LocalDateTime.now())
                .build();
        chatRepository.save(chat);
        return chat.getId();
    }

    @Override
    public List<ChatVO> getList() {
        String studentId = AuthStorage.getUser().getUserId();
        LambdaQueryWrapper<ChatDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatDO::getStudentId, studentId);
        queryWrapper.orderByDesc(ChatDO::getUpdateTime);
        List<ChatDO> chatList = chatRepository.list(queryWrapper);
        List<ChatVO> chatVOList = new ArrayList<>();
        chatList.forEach(chat -> chatVOList.add(new ChatVO(chat)));
        return chatVOList;
    }

    @Override
    public Void delete(Long chatId) {
        if(chatId == null) throw new OpException(OpExceptionEnum.CHAT_ID_EMPTY);
        chatRepository.removeById(chatId);
        return null;
    }
}
