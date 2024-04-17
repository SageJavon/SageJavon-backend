package com.springboot.cli.common.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springboot.cli.common.AppProperties;
import io.jsonwebtoken.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import com.google.gson.GsonBuilder;
import com.xhpolaris.idlgen.basic.UserMeta;
import com.google.protobuf.util.JsonFormat;

/**
 * date: 2021-01-05 08:48
 * description token管理
 *
 * @author qiDing
 */
@Slf4j
@ApiModel("token提供者")
public class TokenProvider {

    private static AppProperties appProperties;

    @ApiModelProperty("盐")
    private static final String SALT_KEY = "links";

    @ApiModelProperty("令牌有效期毫秒")
    private static final long TOKEN_VALIDITY = 86400000;

    @ApiModelProperty("权限密钥")
    private static final String AUTHORITIES_KEY = "auth";

    @ApiModelProperty("Base64 密钥")
    private final static String SECRET_KEY = Base64.getEncoder().encodeToString(SALT_KEY.getBytes(StandardCharsets.UTF_8));


    /**
     * 生成token
     *
     * @param userId   用户id
     * @param clientId 用于区别客户端，如移动端，网页端，此处可根据自己业务自定义
     * @param role     角色权限
     */
    public static String createToken(String userId, String clientId, String role) {
        Date validity = new Date((new Date()).getTime() + TOKEN_VALIDITY);
        return Jwts.builder()
                // 代表这个JWT的主体，即它的所有人
                .setSubject(String.valueOf(userId))
                // 代表这个JWT的签发主体
                .setIssuer("")
                // 是一个时间戳，代表这个JWT的签发时间；
                .setIssuedAt(new Date())
                // 代表这个JWT的接收对象
                .setAudience(clientId)
                .claim("role", role)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 校验token
     */
    public static JwtUser checkToken(String token) {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String audience = claims.getAudience();
            String userId = claims.get("userId", String.class);
            String role = claims.get("role", String.class);
            JwtUser jwtUser = new JwtUser().setUserId(userId).setRole(role).setValid(true);
            log.info("===token有效{},客户端{}", jwtUser, audience);
            return jwtUser;
        }
        log.error("***token无效***");
        return new JwtUser();
    }

    @SneakyThrows
    public static UserMeta decodeToken(String rememberMeToken) {

        byte[] publicKeyBytes = parsePublicKeyString(appProperties.getPublicKey());

        // 转换为公钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey  = keyFactory.generatePublic(keySpec);

            // 验证 JWT
            DecodedJWT decodedJWT = JWT.require(Algorithm.ECDSA256((ECPublicKey) publicKey, null))
                    .build()
                    .verify(rememberMeToken);
            log.info("JWT verification successful!");
            String string = new String(Base64.getDecoder().decode(decodedJWT.getPayload()));
            fromJson(string, UserMeta.class);

            UserMeta.Builder builder = UserMeta.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(string, builder);

            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] parsePublicKeyString(String publicKeyString) {
        // 去除开头和结尾的标记，并移除换行符
        String base64PublicKey = publicKeyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        // 将 Base64 编码的字符串解码为字节数组
        return Base64.getDecoder().decode(base64PublicKey);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return new GsonBuilder().create().fromJson(json, clazz);
    }


    private static boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }
}
