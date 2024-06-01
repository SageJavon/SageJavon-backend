package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.HistoryMapper;
import com.springboot.cli.model.DO.HistoryDO;
import com.springboot.cli.repository.IHistoryRepo;
import org.springframework.stereotype.Service;

@Service
public class HistoryRepository extends ServiceImpl<HistoryMapper, HistoryDO> implements IHistoryRepo {
}
