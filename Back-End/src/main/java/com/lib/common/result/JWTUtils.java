package com.lib.common.result;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT tool class
 */
public class JWTUtils {

    private static final String SECERT = "rick";

    /**
     * Generate Token
     * @return
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        // Set payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        // Set expire time
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,7);
        Map<String,Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ","JWT");
        return builder.withHeader(header)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SECERT));
    }

    /**
     * Verify Token
     * @return
     *     DecodedJWT  可以用来获取用户信息
     */
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SECERT)).build().verify(token);
    }
}
