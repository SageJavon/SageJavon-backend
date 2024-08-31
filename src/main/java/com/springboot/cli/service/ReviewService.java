package com.springboot.cli.service;

public interface ReviewService {
    Integer review(Long exerciseId, Integer review);

    Integer getReview(String studentId, Long exerciseId);
}
