package com.springboot.cli.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.springboot.cli.model.TestDO;
import com.springboot.cli.repository.ITestRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Resource
    private ITestRepo testRepo;


    @Override
    public void save(TestDO testDO) {
        testRepo.saveOrUpdate(testDO);

//        LambdaQueryWrapper<TestDO> wrapper = Wrappers.lambdaQuery();
//
//        wrapper.eq(TestDO::getId, testDO.getId());
//
//        TestDO result = testRepo.getBaseMapper().selectOne(wrapper);

//        List<TraJobScheduleDO> jobScheduleList = traJobScheduleRepo.lambdaQuery().in(TraJobScheduleDO::getModelKey, modelKeyList).list();
//        return jobScheduleList.stream().map(JobSchedule::build).collect(Collectors.toList());
    }
}
