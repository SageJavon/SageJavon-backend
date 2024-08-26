package com.springboot.cli.service.impl;

import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.TutorHistoryDO;
import com.springboot.cli.model.VO.SMessageVO;
import com.springboot.cli.repository.impl.TutorHistoryRepository;
import com.springboot.cli.service.TutorHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TutorHistoryServiceImpl implements TutorHistoryService {
    @Resource
    private TutorHistoryRepository tutorHistoryRepository;

    @Override
    public Void buildMessage(List<SMessageVO> sMessageList) {
        List<TutorHistoryDO> batchList = new ArrayList<>();
        for(SMessageVO sMessage : sMessageList) {
            TutorHistoryDO history = TutorHistoryDO.builder()
                    .studentId(AuthStorage.getUser().getUserId())
                    .role(sMessage.getRole())
                    .content(sMessage.getContent())
                    .timeStamp(LocalDateTime.now())
                    .build();
            batchList.add(history);
        }
        tutorHistoryRepository.saveBatch(batchList);
        return null;
    }
}
