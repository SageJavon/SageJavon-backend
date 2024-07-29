package com.springboot.cli.controller;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.exercise.*;
import com.springboot.cli.service.ExerciseRecordService;
import com.springboot.cli.service.ExerciseService;
import com.springboot.cli.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/question")
public class ExerciseController {
    @Resource
    private ExerciseService exerciseService;

    @Resource
    private KnowledgeService knowledgeService;

    @Resource
    private ExerciseRecordService exerciseRecordService;

    @GetMapping("/recommend")
    public BaseResponse<List<ExerciseVO>> getRecList(Integer questionNum, Integer difficultyOrder) {
        if(questionNum == null || difficultyOrder == null) return BaseResponse.buildBizEx(OpExceptionEnum.ILLEGAL_ARGUMENT);
        try {
            List<ExerciseDO> exerciseList = exerciseService.getRecList(questionNum);
            if(exerciseList == null || exerciseList.isEmpty())
                return BaseResponse.buildSuccess(null);
            List<ExerciseVO> resultList = new ArrayList<>();
            for (ExerciseDO exercise : exerciseList) {
                List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(exercise.getId());
                Integer done = exerciseRecordService.hasDoneExercise(AuthStorage.getUser().getUserId(), exercise.getId());
                ExerciseVO exerciseVO = new ExerciseVO(exercise, knowledgeList, done);
                resultList.add(exerciseVO);
            }
            if (difficultyOrder == 1)
                resultList.sort(Comparator.comparingInt(ExerciseVO::getDifficulty));
            if (difficultyOrder == 2)
                resultList.sort((o1, o2) -> Integer.compare(o2.getDifficulty(), o1.getDifficulty()));
            return BaseResponse.buildSuccess(resultList);
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        }
    }

    @GetMapping("/record/list")
    public BaseResponse<ExerciseRecordVOPage> getRecordList(@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        if (pageSize == null || pageNum == null || pageNum < 1 || pageSize < 0)
            return BaseResponse.buildBizEx(OpExceptionEnum.ILLEGAL_ARGUMENT);
        try {
            ExerciseRecordPage exerciseRecordPage = exerciseRecordService.page(pageSize, pageNum, AuthStorage.getUser().getUserId());
            List<ExerciseRecordDO> exerciseRecordList = exerciseRecordPage.getExerciseRecordList();
            if(exerciseRecordList == null || exerciseRecordList.isEmpty())
                return BaseResponse.buildSuccess(null);
            List<ExerciseRecordVO> resultList = new ArrayList<>();
            for (ExerciseRecordDO exerciseRecord : exerciseRecordList) {
                List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(exerciseRecord.getExerciseId());
                ExerciseDO exercise = exerciseService.getExerciseById(exerciseRecord.getExerciseId());
                resultList.add(new ExerciseRecordVO(exercise, knowledgeList, exerciseRecord));
            }
            return BaseResponse.buildSuccess(new ExerciseRecordVOPage(resultList, exerciseRecordPage.getTotal(), exerciseRecordPage.getPages()));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        }
    }

    @GetMapping("/list")
    public BaseResponse<ExerciseVOPage> getExerciseList(Integer type, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Integer difficulty, String knowledgeId, Integer difficultyOrder) {
        try {
            List<Long> knowledgeIdList = null;
            if (knowledgeId != null && !knowledgeId.isEmpty())
                knowledgeIdList = Arrays.stream(knowledgeId.split(",")).map(Long::parseLong).collect(Collectors.toList());
            if (pageSize == null || pageNum == null || pageNum < 1 || pageSize < 0 || difficultyOrder == null)
                return BaseResponse.buildBizEx(OpExceptionEnum.ILLEGAL_ARGUMENT);
            ExercisePage exercisePage = exerciseService.page(type, pageNum, pageSize, difficulty, knowledgeIdList, difficultyOrder);
            List<ExerciseDO> exerciseList = exercisePage.getExerciseList();
            if(exerciseList == null || exerciseList.isEmpty())
                return BaseResponse.buildSuccess(null);
            List<ExerciseVO> resultList = new ArrayList<>();
            for (ExerciseDO exercise : exerciseList) {
                List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(exercise.getId());
                Integer done = exerciseRecordService.hasDoneExercise(AuthStorage.getUser().getUserId(), exercise.getId());
                ExerciseVO exerciseVO = new ExerciseVO(exercise, knowledgeList, done);
                resultList.add(exerciseVO);
            }
            return BaseResponse.buildSuccess(new ExerciseVOPage(resultList, exercisePage.getTotal(), exercisePage.getPages()));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        }
    }

    @GetMapping("/knowledge")
    public BaseResponse<List<KnowledgeVO>> getKnowledge() {
        try {
            List<KnowledgeDO> knowledgeList = knowledgeService.getKnowledgeList();
            List<KnowledgeVO> resultList = new ArrayList<>();
            knowledgeList.forEach(knowledge -> resultList.add(new KnowledgeVO(knowledge)));
            return BaseResponse.buildSuccess(resultList);
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        }
    }
}
