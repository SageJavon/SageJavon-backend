package com.springboot.cli.controller;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.ExerciseDO;
import com.springboot.cli.model.DO.ExerciseRecordDO;
import com.springboot.cli.model.VO.AllInformationVO;
import com.springboot.cli.model.VO.exercise.CodeExerciseVO;
import com.springboot.cli.model.VO.exercise.KnowledgeVO;
import com.springboot.cli.service.ExerciseRecordService;
import com.springboot.cli.service.ExerciseService;
import com.springboot.cli.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/homepage")
public class HomePageController {

    @Resource
    private ExerciseService exerciseService;

    @Resource
    private ExerciseRecordService exerciseRecordService;

    @Resource
    private KnowledgeService knowledgeService;

    @GetMapping
    public BaseResponse<AllInformationVO> getAllInformation() {
        try {
            Integer codeNumber = exerciseService.getExerciseLNumber(0);
            Integer selectNumber = exerciseService.getExerciseLNumber(1);
            List<ExerciseRecordDO> allExerciseRecord = exerciseRecordService.getStudentAllExerciseRecord(AuthStorage.getUser().getUserId());
            LocalDateTime maxSubmitTime = getMaxSubmitTime(allExerciseRecord);

            List<Long> top10ExerciseIds = getTop10PopularExerciseIds(allExerciseRecord);
            List<CodeExerciseVO> codeExerciseVOList = new ArrayList<>();
            for (Long id : top10ExerciseIds) {
                ExerciseDO exercise = exerciseService.getExerciseById(id);
                List<KnowledgeVO> knowledgeList = knowledgeService.getKnowledgeList(id);
                Integer done = exerciseRecordService.hasDoneExercise(AuthStorage.getUser().getUserId(), id);
                CodeExerciseVO codeExercise = new CodeExerciseVO(exercise, knowledgeList, done);
                codeExerciseVOList.add(codeExercise);
            }

            Integer continuousSolveDays = getContinuousSolveDays(allExerciseRecord);
            Integer solveCount = getSolveCount(allExerciseRecord);
            Map<LocalDate, Integer> solvePerDay = getSolvePerDay(allExerciseRecord);

            return BaseResponse.buildSuccess(new AllInformationVO(codeNumber,selectNumber,maxSubmitTime,codeExerciseVOList,continuousSolveDays,solveCount,solvePerDay));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        }
    }

    public static LocalDateTime getMaxSubmitTime(List<ExerciseRecordDO> allExercise) {
        return allExercise.stream()
                .map(ExerciseRecordDO::getSubmitTime)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    private Map<LocalDate, Integer> getSolvePerDay(List<ExerciseRecordDO> allExercise) {
        // 获取当前日期
        LocalDate now = LocalDate.now();

        // 过滤出近一个月的做题记录
        List<ExerciseRecordDO> recentExercises = allExercise.stream()
                .filter(record -> record.getSubmitTime().toLocalDate().isAfter(now.minusMonths(1)))
                .collect(Collectors.toList());

        // 统计每一天的做题数量
        Map<LocalDate, List<Long>> exerciseCountPerDay = new HashMap<>();
        for (ExerciseRecordDO record : recentExercises) {
            LocalDate submitDate = record.getSubmitTime().toLocalDate();
            exerciseCountPerDay.computeIfAbsent(submitDate, k -> new ArrayList<>()).add(record.getExerciseId());
        }

        // 计算每一天的不同 exerciseId 的数量
        Map<LocalDate, Integer> solvePerDay = new HashMap<>();
        for (Map.Entry<LocalDate, List<Long>> entry : exerciseCountPerDay.entrySet()) {
            List<Long> exerciseIds = entry.getValue();
            int distinctExerciseCount = exerciseIds.stream().distinct().collect(Collectors.toList()).size();
            solvePerDay.put(entry.getKey(), distinctExerciseCount);
        }
        return solvePerDay;
    }

    public int getContinuousSolveDays(List<ExerciseRecordDO> allExercise) {
        // 获取当前日期
        LocalDate now = LocalDate.now();

        // 过滤出近一个月的做题记录
        List<LocalDate> solveDates = allExercise.stream()
                .map(record -> record.getSubmitTime().toLocalDate())
                .distinct()
                .sorted((d1, d2) -> d2.compareTo(d1)) // 降序排序
                .collect(Collectors.toList());

        int continuousDays = 0;
        LocalDate lastDate = now.minusDays(1); // 从昨天开始

        for (LocalDate date : solveDates) {
            if (date.equals(lastDate)) {
                continuousDays++;
                lastDate = lastDate.minusDays(1);
            } else if (date.isBefore(lastDate)) {
                break;
            }
        }

        return continuousDays;
    }

    public int getSolveCount(List<ExerciseRecordDO> allExercise) {
        // 获取所有不同的 exerciseId
        List<Long> distinctExerciseIds = allExercise.stream()
                .map(ExerciseRecordDO::getExerciseId)
                .distinct()
                .collect(Collectors.toList());

        return distinctExerciseIds.size();
    }

    private List<Long> getTop10PopularExerciseIds(List<ExerciseRecordDO> allExercise) {
        // 提取出所有不同的 exercise_id
        Set<Long> exerciseIds = allExercise.stream()
                .map(ExerciseRecordDO::getExerciseId)
                .collect(Collectors.toSet());

        // 存储题目和对应的人数
        Map<Long, Integer> exerciseCountMap = new HashMap<>();

        // 循环遍历 exercise_id，调用 getExerciseRecordsByExerciseID 方法获取每个题目做的人数
        for (Long exerciseId : exerciseIds) {
            int count = exerciseRecordService.getExerciseRecordsByExerciseID(exerciseId);
            exerciseCountMap.put(exerciseId, count);
        }

        // 根据人数对题目进行排序，并取出前 10 个题目
        return exerciseCountMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
