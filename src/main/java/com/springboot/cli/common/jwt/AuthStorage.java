package com.springboot.cli.common.jwt;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


/**
 * 存储本次请求的授权信息，适用于各种业务场景，包括分布式部署
 */
public class AuthStorage {

    @ApiModelProperty("请求头token的下标")
    public static final String TOKEN_KEY = "token";

    /**
     * 模拟session
     */
    private static final HashMap<String, JwtUser> JWT_USER = new HashMap<String, JwtUser>();

    /**
     * 全局获取用户
     */
    public static JwtUser getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        JwtUser jwtUser = JWT_USER.get(request.getHeader(TOKEN_KEY));
        if (jwtUser == null) throw new OpException(OpExceptionEnum.JWT_ERROR);
        return jwtUser;
    }

    /**
     * 设置用户
     */
    public static void setUser(String token, JwtUser user) {
        JWT_USER.put(token, user);
    }

    /**
     * 清除授权
     */
    public static void clearUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        JWT_USER.remove(request.getHeader(TOKEN_KEY));
    }
}
