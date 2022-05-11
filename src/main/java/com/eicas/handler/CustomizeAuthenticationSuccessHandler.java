package com.eicas.handler;

import cn.hutool.jwt.JWTUtil;

import com.eicas.common.ResultData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author osnudt
 * @since 2022/4/12
 */

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter out = httpServletResponse.getWriter();

        UserDetails principal = (UserDetails) authentication.getPrincipal();

//        JSONObject.parse(JSONObject.toJSONString(principal));
//
//        Map<String, Object> map = (Map<String, Object>) JSON.parseObject(String.valueOf(JSONObject.parse(JSONObject.toJSONString(principal))), Map.class);

//        String token = JWTUtil.createToken(map,new byte[123]);

       // ResultData result = ResultData.success(token,"用户登录成功");
       // out.write(JSON.toJSONString(result));
    }
}
