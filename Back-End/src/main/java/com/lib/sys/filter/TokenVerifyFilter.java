package com.lib.sys.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lib.common.constant.SystemConstant;
import com.lib.common.result.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenVerifyFilter extends BasicAuthenticationFilter {
    public TokenVerifyFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    /**
     * Determine if submitted Token legal
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Request Token from request
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith(SystemConstant.SYS_TOKEN_PREFIX)){
            // Transfer token info with correct prefix
            // 1. Get original token
            String token = header.replace(SystemConstant.SYS_TOKEN_PREFIX, "");
            // Verify token info is legal
            DecodedJWT verify = JWTUtils.verify(token);
            if(verify == null){
                responseLogin(response);
            }
            // Legal to get here
            // Obtain login account info
            String username = verify.getClaim("username").asString();
            List<GrantedAuthority> list = new ArrayList<>();
            // Bind role to current user
            list.add(new SimpleGrantedAuthority("ADMIN"));
            // Give releavent right to account

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(username,"",list);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // Allow the request
            chain.doFilter(request,response);

        }else{
            // Does not have token or illegal
            responseLogin(response);
        }
    }

    private static void responseLogin(HttpServletResponse response) throws IOException {
        // Verify fails -- inform the user to login
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
        resultMap.put("msg", "Please Login");
        writer.write(JSON.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }
}
