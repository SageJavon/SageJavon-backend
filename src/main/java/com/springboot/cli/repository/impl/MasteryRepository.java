package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.MasteryMapper;
import com.springboot.cli.model.DO.MasteryDO;
import com.springboot.cli.repository.IMasteryRepo;
import org.springframework.stereotype.Service;

@Service
public class MasteryRepository extends ServiceImpl<MasteryMapper, MasteryDO> implements IMasteryRepo {
}
