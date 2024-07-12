package com.springboot.cli.controller;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
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
import java.util.ArrayList;
import java.util.List;

import static com.springboot.cli.common.enums.OpExceptionEnum.ILLEGAL_ARGUMENT;

@RestController
@Slf4j
@RequestMapping("/question/code")
public class CodeExerciseController {
    @Resource
    private ExerciseService exerciseService;

    @Resource
    private KnowledgeService knowledgeService;

    @Resource
    private ExerciseRecordService exerciseRecordService;

    @GetMapping("/detail")
    public BaseResponse<CodeExerciseVO> getExerciseDetail(Long id) {
        ExerciseDO exercise = exerciseService.getExerciseById(id);
        if(exercise == null)
            return BaseResponse.buildSuccess(null);
        List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(id);
        Integer done = exerciseRecordService.hasDoneExercise(AuthStorage.getUser().getUserId(), id);
        CodeExerciseVO codeExercise = new CodeExerciseVO(exercise, knowledgeList, done);
        return BaseResponse.buildSuccess(codeExercise);
    }

    @PostMapping
    public BaseResponse<FeedBackVO> getFeedBack(Long id, String answer, Integer submitNum) {
        if(id == null) return BaseResponse.buildBizEx(ILLEGAL_ARGUMENT);
        try {
            FeedBackVO feedBack = exerciseService.getFeedBack(id, answer, submitNum);
            return BaseResponse.buildSuccess(feedBack);
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        }
    }

    @GetMapping("/record/list")
    public BaseResponse<List<CodeExerciseRecordVO>> getExerciseRecordList(Long questionId) {
        if(questionId == null) return BaseResponse.buildBizEx(ILLEGAL_ARGUMENT);
        ExerciseDO exercise = exerciseService.getExerciseById(questionId);
        if(exercise == null) return BaseResponse.buildBizEx(ILLEGAL_ARGUMENT);
        List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(questionId);
        List<ExerciseRecordDO> exerciseRecordList = exerciseRecordService.getExerciseRecord(AuthStorage.getUser().getUserId(), questionId);
        List<CodeExerciseRecordVO> resultList = new ArrayList<>();
        exerciseRecordList.forEach(record -> resultList.add(new CodeExerciseRecordVO(exercise, knowledgeList, record)));
        return BaseResponse.buildSuccess(resultList);
    }

    @GetMapping("/record/detail")
    public BaseResponse<DetailCodeExerciseRecordVO> getDetailExerciseRecord(Long recordId) {
        if(recordId == null) return BaseResponse.buildBizEx(ILLEGAL_ARGUMENT);
        ExerciseRecordDO exerciseRecord = exerciseRecordService.getExerciseRecord(recordId);
        if(exerciseRecord == null) return BaseResponse.buildBizEx(ILLEGAL_ARGUMENT);
        ExerciseDO exercise = exerciseService.getExerciseById(exerciseRecord.getExerciseId());
        List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(exerciseRecord.getExerciseId());
        return BaseResponse.buildSuccess(new DetailCodeExerciseRecordVO(exercise, knowledgeList, exerciseRecord));
    }
}
