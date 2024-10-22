package com.springboot.cli.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static com.springboot.cli.common.CommonConstants.PYTHON_SERVICE;
import static com.springboot.cli.common.CommonConstants.submitNumThreshold;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Resource
    ExerciseRepository exerciseRepository;

    @Resource
    ExerciseRecordRepository exerciseRecordRepository;

    @Resource
    ExerciseKnowledgeRepository exerciseKnowledgeRepository;

    @Resource
    RestTemplate restTemplate;

    @Override
    public ExerciseDO getExerciseById(Long id) {
        return exerciseRepository.getById(id);
    }

    @Override
    public FeedBackVO getFeedBack(Long id, String answer, Integer submitNum) {
        ExerciseDO exercise = exerciseRepository.getById(id);
        if (exercise == null) throw new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        String url = PYTHON_SERVICE + "/get_code_score";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("question", exercise.getQuestionText());
        requestBody.put("code", answer);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, requestHeaders);
        String result;
        try{
            result = restTemplate.postForObject(url, httpEntity, String.class);
        } catch (Exception e) {
            throw new OpException(OpExceptionEnum.LLM_ERROR);
        }
        if (result == null) throw new OpException(OpExceptionEnum.LLM_ERROR);
        JSONObject json = JSONObject.parseObject(result);
        String data = json.getString("data");
        JSONObject dataJson = JSONObject.parseObject(data);
        String suggestion = dataJson.getString("suggestion");
        Integer codingStyle = dataJson.getInteger("codingStyle");
        Integer functionalCorrectness = dataJson.getInteger("functionalCorrectness");
        Integer usefulness = dataJson.getInteger("usefulness");
        double score = Math.round((codingStyle + functionalCorrectness + usefulness) / 12.0 * 100.0) / 100.0;
        ExerciseRecordDO exerciseRecord = ExerciseRecordDO.builder()
                .exerciseId(id)
                .studentId(AuthStorage.getUser().getUserId())
                .answer(answer)
                .score(score)
                .suggestion(suggestion)
                .type(0)
                .submitTime(LocalDateTime.now())
                .build();
        exerciseRecordRepository.save(exerciseRecord);
        String correctAnswer = null;
        if (submitNum >= submitNumThreshold)
            correctAnswer = exercise.getCorrectAnswer();
        return FeedBackVO.builder()
                .correctAnswer(correctAnswer)
                .score((int)(score * 100))
                .suggestion(suggestion)
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
    public List<ExerciseDO> getRecList(String studentId, Integer questionNum) {
        String url = PYTHON_SERVICE + "/get_recommend_list?userId={studentId}&questionNum={questionNum}";
        String result;
        try{
            result = restTemplate.getForEntity(url, String.class, studentId, questionNum).getBody();
        } catch (Exception e) {
            throw new OpException(OpExceptionEnum.REC_ERROR);
        }
        if (result == null) throw new OpException(OpExceptionEnum.REC_ERROR);
        JSONObject json = JSONObject.parseObject(result);
        String data = json.getString("data");
        JSONObject dataJson = JSONObject.parseObject(data);
        JSONArray jsonArray = dataJson.getJSONArray("qList");
        List<Integer> recQuestionList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            recQuestionList.add(jsonArray.getInteger(i));
        }
        LambdaQueryWrapper<ExerciseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ExerciseDO::getId, recQuestionList);
        return exerciseRepository.list(queryWrapper);
    }

    @Override
    public ExercisePage page(Integer type, Integer pageNum, Integer pageSize, Integer difficulty, List<Long> knowledgeId, Integer difficultyOrder, String chapter) {
        Page<ExerciseDO> page = new Page<>(pageNum, pageSize);
        page.addOrder(new OrderItem("id", true));
        LambdaQueryWrapper<ExerciseDO> queryWrapper = new LambdaQueryWrapper<>();
        // 筛选题目类型
        if (type != null) {
            queryWrapper.eq(ExerciseDO::getType, type);
        }
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
        if (chapter != null) {
            queryWrapper.eq(ExerciseDO::getChapter, chapter);
        }
        if (difficultyOrder != 0)
            queryWrapper.orderBy(true, difficultyOrder == 1, ExerciseDO::getDifficulty);
        page = exerciseRepository.page(page, queryWrapper);
        return new ExercisePage(page.getRecords(), page.getTotal(), page.getPages());
    }
}
