package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.ChatMapper;
import com.springboot.cli.model.DO.ChatDO;
import com.springboot.cli.repository.IChatRepo;
import org.springframework.stereotype.Service;

@Service
public class ChatRepository extends ServiceImpl<ChatMapper, ChatDO> implements IChatRepo {
}
