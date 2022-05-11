package com.eicas.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 鉴权
 */
@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private Set<String> permitUrlSet = null;

    public TokenAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
        permitUrlSet = new HashSet<String>();
        permitUrlSet.add("/login");
        permitUrlSet.add("/oauth/login");
        permitUrlSet.add("/api/oauth/getToken");
    }
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("=================" + request.getRequestURI());
        //需要鉴权

        if (!permitUrlSet.contains(request.getRequestURI())) {
            UsernamePasswordAuthenticationToken authentication = null;
            try {
                authentication = getAuthentication(request);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.error("鉴权失败");
            }
        }
        chain.doFilter(request, response);
    }
 
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = "";
        String authorization = request.getHeader("Authorization");
        if(StringUtils.hasText(authorization)){
            String [] list = authorization.split(" ");
            if(list.length > 1){
                token = list[1];
            }
        }

        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }
        JWT jwt = JWTUtil.parseToken(token);
        JWTPayload payload = jwt.getPayload();
        JSONObject jsonObject =  payload.getClaimsJson();

        String username = String.valueOf(jsonObject.get("username"));

        List authorities = (List) ((List)jsonObject.get("authorities")).stream().map(i-> new SimpleGrantedAuthority(String.valueOf(((JSONObject) i).get("authority")))).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(username,null, authorities);
    }
}