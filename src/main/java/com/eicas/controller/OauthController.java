package com.eicas.controller;

import com.eicas.common.ResultData;
import com.eicas.service.IOAuth2ClientService;
import com.eicas.service.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
public class OauthController {

    @Resource
    IOAuth2ClientService oauth2clientService;

    @Resource
    UserInfoService userService;

    @Value("${ecms.auth-server}")
    private String AUTH_SVR;
    /*
     * 向此地址请求身份认证，颁发授权码
     */
    @Value("${ecms.authorization-url}")
    private String AUTHORIZATION_URL;

    /*
     * 携带授权码向此地址请求访问令牌
     */
    @Value("${ecms.token-url}")
    private String TOKEN_URL;

    @Value("${ecms.client-id}")
    private String CLIENT_ID;

    @Value("${ecms.client-secret}")
    private String CLIENT_SECRET;

    @Value("${ecms.signoff-uri}")
    private String SIGNOFF_URI;


    @GetMapping("/api/oauth/getToken")
    @ResponseBody
    public ResultData<String> getToken (String code) {
        String url = String.format("%s?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s", TOKEN_URL, CLIENT_ID, CLIENT_SECRET,code);
        return oauth2clientService.getToken(url);
    }

    /**
     * 认证中心回调地址并接受授权码, 根据认证中心返回的授权码获取令牌.
     */
    @RequestMapping("/oauth/login")
    public String getAccessToken() {
        System.out.printf("redirect:%s?response_type=code&client_id=%s%n", AUTHORIZATION_URL, CLIENT_ID);
        return String.format("redirect:%s?response_type=code&client_id=%s", AUTHORIZATION_URL, CLIENT_ID);
    }

    @GetMapping("/oauth/loginOut")
    public String loginOut() {
//        clientAuthenticate.setTokenInfo(null);
//        return String.format("redirect:%s/oauth/signoff.do?clientid=%s&secret=%s&redirectUri=%s",
//                clientAuthenticate.getOauthServer(),
//                clientAuthenticate.getClientId(),
//                clientAuthenticate.getClientSecret(),
//                clientAuthenticate.getWebUrl()
//        );
        return null;
    }

}
