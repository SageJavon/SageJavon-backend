package com.springboot.cli.common.config;

import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.common.jwt.JwtUser;
import com.springboot.cli.common.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 *
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AuthStorage.TOKEN_KEY);
        if (StringUtils.hasText(token) && token.startsWith("Berry ")) {
            token = token.substring(6);
        }
        else token = null;
        if (StringUtils.hasLength(token)) {
            UserMeta tokens = TokenProvider.decodeToken(token);
            JwtUser jwtUser = new JwtUser().setUserId(tokens.getUserId()).setValid(true);
            // 是否认证通过
            if (StringUtils.hasLength(jwtUser.getUserId()) && jwtUser.isValid()) {
                // 保存授权信息
                AuthStorage.setUser(token, jwtUser);
                return true;
            }
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("请先登录！");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成清除授权信息
        AuthStorage.clearUser();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
