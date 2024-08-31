package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.ReviewMapper;
import com.springboot.cli.model.DO.ReviewDO;
import com.springboot.cli.repository.IReviewRepo;
import org.springframework.stereotype.Service;

@Service
public class ReviewRepository extends ServiceImpl<ReviewMapper, ReviewDO> implements IReviewRepo {
}
