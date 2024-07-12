package com.springboot.cli.controller;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import com.springboot.cli.model.VO.exercise.*;
import com.springboot.cli.service.ExerciseRecordService;
import com.springboot.cli.service.ExerciseService;
import com.springboot.cli.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.springboot.cli.common.enums.OpExceptionEnum.ILLEGAL_ARGUMENT;

@RestController
@Slf4j
@RequestMapping("/question/select")
public class SelectExerciseController {
    @Resource
    private ExerciseService exerciseService;

    @Resource
    private KnowledgeService knowledgeService;

    @Resource
    private ExerciseRecordService exerciseRecordService;

    @GetMapping("/detail")
    public BaseResponse<SelectExerciseVO> getExerciseDetail(Long id) {
        ExerciseDO exercise = exerciseService.getExerciseById(id);
        if(exercise == null)
            return BaseResponse.buildSuccess(null);
        List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(id);
        SelectExerciseVO selectExercise = new SelectExerciseVO(exercise, knowledgeList);
        return BaseResponse.buildSuccess(selectExercise);
    }

    @PostMapping
    public BaseResponse<FeedBackVO> getFeedBack(Long id, String choice) {
        if(id == null || choice == null) return BaseResponse.buildBizEx(ILLEGAL_ARGUMENT);
        try {
            FeedBackVO feedBack = exerciseService.getSelectFeedBack(id, choice);
            return BaseResponse.buildSuccess(feedBack);
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        }
    }

    @GetMapping("/record/detail")
    public BaseResponse<DetailSelectExerciseRecordVO> getDetailExerciseRecord(Long recordId) {
        if(recordId == null) return BaseResponse.buildBizEx(ILLEGAL_ARGUMENT);
        ExerciseRecordDO exerciseRecord = exerciseRecordService.getExerciseRecord(recordId);
        if(exerciseRecord == null) return BaseResponse.buildBizEx(ILLEGAL_ARGUMENT);
        ExerciseDO exercise = exerciseService.getExerciseById(exerciseRecord.getExerciseId());
        List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(exerciseRecord.getExerciseId());
        return BaseResponse.buildSuccess(new DetailSelectExerciseRecordVO(exercise, knowledgeList, exerciseRecord));
    }
}
