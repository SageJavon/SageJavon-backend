package com.springboot.cli.model.VO.exercise;

import com.springboot.cli.model.DO.ExerciseRecordDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseRecordPage {
    private List<ExerciseRecordDO> exerciseRecordList;
    private Long total;
    private Long pages;
}
