package com.springboot.cli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.DO.ExerciseKnowledgeDO;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import com.springboot.cli.model.VO.exercise.ExercisePage;
import com.springboot.cli.model.VO.exercise.FeedBackVO;
import com.springboot.cli.repository.impl.ExerciseKnowledgeRepository;
import com.springboot.cli.repository.impl.ExerciseRecordRepository;
import com.springboot.cli.repository.impl.ExerciseRepository;
import com.springboot.cli.service.ExerciseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.springboot.cli.common.CommonConstants.submitNumThreshold;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Resource
    ExerciseRepository exerciseRepository;

    @Resource
    ExerciseRecordRepository exerciseRecordRepository;

    @Resource
    ExerciseKnowledgeRepository exerciseKnowledgeRepository;

    @Override
    public ExerciseDO getExerciseById(Long id) {
        return exerciseRepository.getById(id);
    }

    @Override
    public FeedBackVO getFeedBack(Long id, String answer, Integer submitNum) {
        if (answer != null) {
            ExerciseRecordDO exerciseRecord = ExerciseRecordDO.builder()
                    .exerciseId(id)
                    .studentId(AuthStorage.getUser().getUserId())
                    .answer(answer)
                    .score(1.0)
                    .type(0)
                    .submitTime(LocalDateTime.now())
                    .build();
            exerciseRecordRepository.save(exerciseRecord);
        }
        String correctAnswer = null;
        if (submitNum >= submitNumThreshold) {
            ExerciseDO exercise = exerciseRepository.getById(id);
            if (exercise == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
            correctAnswer = exercise.getCorrectAnswer();
        }
        return FeedBackVO.builder()
                .correctAnswer(correctAnswer)
                .score(100)
                .build();
    }

    @Override
    public FeedBackVO getSelectFeedBack(Long id, String choice) {
        ExerciseDO exercise = exerciseRepository.getById(id);
        if (exercise == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        String correctAnswer = exercise.getCorrectAnswer();
        if (choice.equals(correctAnswer)) {
            ExerciseRecordDO exerciseRecord = ExerciseRecordDO.builder()
                    .exerciseId(id)
                    .studentId(AuthStorage.getUser().getUserId())
                    .answer(choice)
                    .score(1.0)
                    .type(1)
                    .submitTime(LocalDateTime.now())
                    .build();
            exerciseRecordRepository.save(exerciseRecord);
            return FeedBackVO.builder()
                    .score(1)
                    .build();
        } else {
            ExerciseRecordDO exerciseRecord = ExerciseRecordDO.builder()
                    .exerciseId(id)
                    .studentId(AuthStorage.getUser().getUserId())
                    .answer(choice)
                    .score(0.0)
                    .type(1)
                    .submitTime(LocalDateTime.now())
                    .build();
            exerciseRecordRepository.save(exerciseRecord);
            return FeedBackVO.builder()
                    .score(0)
                    .build();
        }
    }

    @Override
    public Integer getExerciseLNumber(int type) {
        return exerciseRepository.getExerciseNumber(type);
    }

    @Override
    public List<ExerciseDO> getRecList(Integer questionNum) {
        return exerciseRepository.getRecList(questionNum);
    }

    @Override
    public ExercisePage page(Integer type, Integer pageNum, Integer pageSize, Integer difficulty, List<Long> knowledgeId) {
        Page<ExerciseDO> page = new Page<>(pageNum, pageSize);
        page.addOrder(new OrderItem("id", true));
        LambdaQueryWrapper<ExerciseDO> queryWrapper = new LambdaQueryWrapper<>();
        if (difficulty != null)
            queryWrapper.eq(ExerciseDO::getDifficulty, difficulty);
        if (knowledgeId != null && !knowledgeId.isEmpty()) {
            LambdaQueryWrapper<ExerciseKnowledgeDO> exerciseKnowledgeDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            exerciseKnowledgeDOLambdaQueryWrapper.in(ExerciseKnowledgeDO::getKnowledgeId, knowledgeId);
            List<ExerciseKnowledgeDO> exerciseKnowledgeList = exerciseKnowledgeRepository.list(exerciseKnowledgeDOLambdaQueryWrapper);
            List<Long> exerciseId = new ArrayList<>();
            exerciseKnowledgeList.forEach(exerciseKnowledge -> exerciseId.add(exerciseKnowledge.getExerciseId()));
            queryWrapper.in(ExerciseDO::getId, exerciseId);
        }
        page = exerciseRepository.page(page, queryWrapper);
        return new ExercisePage(page.getRecords(), page.getTotal(), page.getPages());
    }
}
