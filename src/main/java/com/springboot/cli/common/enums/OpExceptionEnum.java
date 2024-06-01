package com.springboot.cli.common.enums;

import com.springboot.cli.common.exception.OpResolve;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.springboot.cli.common.enums.CommonResolveEnum.CHECK_ARGUMENT;
import static com.springboot.cli.common.enums.CommonResolveEnum.CONTACT_RD;


/**
 * 此枚举用于构造OpException
 */
@AllArgsConstructor
@Getter
public enum OpExceptionEnum {

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "非法参数", CHECK_ARGUMENT),

    EXECUTE_FAIL("EXECUTE_FAIL", "语句执行失败", CONTACT_RD),

    USER_ID_EMPTY("USER_ID_EMPTY", "用户ID为空", CHECK_ARGUMENT),

    CHAT_ID_EMPTY("CHAT_ID_EMPTY", "ChatID为空", CHECK_ARGUMENT),

    CHAT_NOT_EXIST("CHAT_NOT_EXIST", "Chat不存在", CHECK_ARGUMENT),
    ;
    final String code;
    final String message;
    final OpResolve resolve;
}
