package com.springboot.cli.controller;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.utils.JsonUtil;
import com.springboot.cli.model.DO.KnowledgeDO;
import com.springboot.cli.model.VO.KnowledgeVO;
import com.springboot.cli.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Resource
    private KnowledgeService knowledgeService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @PostMapping
    public BaseResponse<Void> uploadKnowledgeGraph(@RequestBody List<KnowledgeDO> modelList) {
        log.info("job schedule save request : {}", JsonUtil.toJsonString(modelList));
        //如果传值为空，视为不更新
        if(modelList == null || modelList.isEmpty()) return BaseResponse.buildSuccess(null);
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(@NotNull TransactionStatus tran) {
                    try {
                        //如果存在记录则更新，否则创建记录
                        knowledgeService.save(modelList);
                    } catch (Exception e) {
                        log.error("save error", e);
                        tran.setRollbackOnly();
                        throw new OpException(OpExceptionEnum.EXECUTE_FAIL, e);
                    }
                }
            });
            return BaseResponse.buildSuccess(null);
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping
    public BaseResponse<List<KnowledgeVO>> getKnowledgeGraph(String studentId) {
        log.info("Get knowledge graph : studentId={}", studentId);
        try {
            return BaseResponse.buildSuccess(knowledgeService.get(studentId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
