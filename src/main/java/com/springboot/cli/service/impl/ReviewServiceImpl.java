package com.springboot.cli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.ReviewDO;
import com.springboot.cli.repository.impl.ReviewRepository;
import com.springboot.cli.service.ReviewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Resource
    private ReviewRepository reviewRepository;

    @Override
    public Integer review(Long exerciseId, Integer review) {
        if (exerciseId == null || review == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        String studentId = AuthStorage.getUser().getUserId();
        LambdaQueryWrapper<ReviewDO> reviewDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        reviewDOLambdaQueryWrapper.eq(ReviewDO::getExerciseId, exerciseId);
        reviewDOLambdaQueryWrapper.eq(ReviewDO::getStudentId, studentId);
        ReviewDO reviewDO = reviewRepository.getOne(reviewDOLambdaQueryWrapper);
        if (reviewDO == null) {
            reviewDO = ReviewDO.builder()
                    .studentId(studentId)
                    .exerciseId(exerciseId)
                    .review(review)
                    .build();
            reviewRepository.save(reviewDO);
        }
        else {
            reviewDO.setReview(review);
            reviewRepository.updateById(reviewDO);
        }
        return review;
    }

    @Override
    public Integer getReview(String studentId, Long exerciseId) {
        if (exerciseId == null || studentId == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        LambdaQueryWrapper<ReviewDO> reviewDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        reviewDOLambdaQueryWrapper.eq(ReviewDO::getExerciseId, exerciseId);
        reviewDOLambdaQueryWrapper.eq(ReviewDO::getStudentId, studentId);
        ReviewDO reviewDO = reviewRepository.getOne(reviewDOLambdaQueryWrapper);
        return reviewDO == null ? 0 : reviewDO.getReview();
    }
}
