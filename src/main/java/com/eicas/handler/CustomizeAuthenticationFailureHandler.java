package com.eicas.handler;

import cn.hutool.json.JSONUtil;
import com.eicas.common.ResultCode;
import com.eicas.common.ResultData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author osnudt
 * @since 2022/4/12
 */

@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        ResultData<String> result = ResultData.failed(ResultCode.USER_CREDENTIALS_ERROR,"用户登录失败");
        out.write(JSONUtil.toJsonStr(result));
    }
}
