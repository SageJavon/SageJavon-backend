package com.springboot.cli.controller;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.utils.JsonUtil;
import com.springboot.cli.model.DO.TestDO;
import com.springboot.cli.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @Resource
    private TransactionTemplate transactionTemplate;//事务

    @PostMapping("/edit")
    public BaseResponse<Boolean> edit(@RequestBody TestDO model) {
        log.info("job schedule save request : {}", JsonUtil.toJsonString(model));
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(@NotNull TransactionStatus tran) {
                    try {
                        testService.save(model);
                    } catch (Exception e) {
                        log.error("save error", e);
                        tran.setRollbackOnly();
                        throw new OpException(OpExceptionEnum.EXECUTE_FAIL, e);
                    }
                }
            });
            return BaseResponse.buildSuccess(true);
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

}
