package com.springboot.cli.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.MasteryMapper;
import com.springboot.cli.model.DO.MasteryDO;

public class MasteryRepository extends ServiceImpl<MasteryMapper, MasteryDO> implements IMasteryRepo {
}
