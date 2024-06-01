package com.springboot.cli.service.impl;

import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import com.springboot.cli.model.VO.exercise.FeedBackVO;
import com.springboot.cli.repository.impl.ExerciseRecordRepository;
import com.springboot.cli.repository.impl.ExerciseRepository;
import com.springboot.cli.service.ExerciseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.springboot.cli.common.CommonConstants.submitNumThreshold;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Resource
    ExerciseRepository exerciseRepository;

    @Resource
    ExerciseRecordRepository exerciseRecordRepository;

    @Override
    public List<ExerciseDO> getExerciseList(int type) {
        return exerciseRepository.getExerciseList(0);
    }

    @Override
    public ExerciseDO getExerciseDetail(Long id) {
        return exerciseRepository.getById(id);
    }

    @Override
    public FeedBackVO getFeedBack(Long id, String answer, Integer submitNum) {
        if(answer != null) {
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
        if(submitNum >= submitNumThreshold) {
            ExerciseDO exercise = exerciseRepository.getById(id);
            if(exercise == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
            correctAnswer = exercise.getCorrectAnswer();
        }
        return FeedBackVO.builder()
                .correctAnswer(correctAnswer)
                .score(100)
                .build();
    }
}
