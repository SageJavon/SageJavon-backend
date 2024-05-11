package com.springboot.cli.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.TestMapper;
import com.springboot.cli.model.DO.TestDO;
import org.springframework.stereotype.Service;

@Service
public class TestRepository extends ServiceImpl<TestMapper, TestDO> implements ITestRepo{
}
