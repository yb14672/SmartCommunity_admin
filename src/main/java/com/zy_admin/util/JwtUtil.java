package com.zy_admin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {

    // token时效：24小时
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 签名哈希的密钥，对于不同的加密算法来说含义不同
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 根据用户id和昵称生成token
     * @param id  用户id
     * @param userName 用户昵称
     * @return JWT规则生成的token
     */
    public static String getJwtToken(String id, String userName){
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("baobao-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("userName", userName)
                // HS256算法实际上就是MD5加盐值，此时APP_SECRET就代表盐值
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }
    /**
     * 判断token是否存在与有效
     * @param jwtToken token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request Http请求对象
     * @return 解析token后获得的用户id
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) { return ""; }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
