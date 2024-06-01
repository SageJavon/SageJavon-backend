package com.springboot.cli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.model.DO.ChatDO;
import com.springboot.cli.model.DO.HistoryDO;
import com.springboot.cli.model.VO.MessageVO;
import com.springboot.cli.model.VO.SMessageVO;
import com.springboot.cli.repository.impl.ChatRepository;
import com.springboot.cli.repository.impl.HistoryRepository;
import com.springboot.cli.service.HistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ChatRepository chatRepository;

    @Resource
    private HistoryRepository historyRepository;

    @Override
    @Transactional
    public List<MessageVO> buildMessage(Long chatId, List<SMessageVO> sMessageList) {
        if (chatId == null) throw new OpException(OpExceptionEnum.CHAT_ID_EMPTY);
        ChatDO chat = chatRepository.getById(chatId);
        if(chat == null) throw new OpException(OpExceptionEnum.CHAT_NOT_EXIST);
        int sort = chat.getSort();
        List<HistoryDO> batchList = new ArrayList<>();
        for(SMessageVO sMessage : sMessageList) {
            sort++;
            HistoryDO history = HistoryDO.builder()
                    .role(sMessage.getRole())
                    .chatId(chatId)
                    .content(sMessage.getContent())
                    .timeStamp(LocalDateTime.now())
                    .sort(sort)
                    .build();
            batchList.add(history);
        }
        historyRepository.saveBatch(batchList);
        //chat
        chat.setSort(sort);
        chat.setUpdateTime(LocalDateTime.now());
        chatRepository.updateById(chat);
        return getMessageVOList(chatId);
    }

    @Override
    public List<MessageVO> getHistory(Long chatId) {
        if (chatId == null) throw new OpException(OpExceptionEnum.CHAT_ID_EMPTY);
        return getMessageVOList(chatId);
    }

    private List<MessageVO> getMessageVOList(Long chatId) {
        LambdaQueryWrapper<HistoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryDO::getChatId, chatId);
        queryWrapper.orderByAsc(HistoryDO::getSort);
        List<HistoryDO> historyList = historyRepository.list(queryWrapper);
        List<MessageVO> messageList = new ArrayList<>();
        historyList.forEach(history -> messageList.add(new MessageVO(history)));
        return messageList;
    }
}
