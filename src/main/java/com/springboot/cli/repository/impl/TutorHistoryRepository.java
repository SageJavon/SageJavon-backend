package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.TutorHistoryMapper;
import com.springboot.cli.model.DO.TutorHistoryDO;
import com.springboot.cli.repository.ITutorHistoryRepo;
import org.springframework.stereotype.Service;

@Service
public class TutorHistoryRepository extends ServiceImpl<TutorHistoryMapper, TutorHistoryDO> implements ITutorHistoryRepo {
}
