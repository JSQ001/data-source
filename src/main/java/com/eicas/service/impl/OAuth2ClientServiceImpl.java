package com.eicas.service.impl;

import com.alibaba.fastjson.JSON;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.eicas.common.ResultData;
import com.eicas.entity.UserInfo;
import com.eicas.service.IOAuth2ClientService;
import com.eicas.service.UserInfoService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author osnudt
 * @since 2022/5/1
 */

@Service
@Slf4j
public class OAuth2ClientServiceImpl implements IOAuth2ClientService {

    @Resource
    RestTemplate restTemplate;
    @Resource
    UserInfoService userInfoService;


    /**
     * 解析认证中心返回数据
     */
    @Override
    public UserInfo parseResponseData(String data) {
        UserInfo user = new UserInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(data);

            JsonNode userInfo = jsonNode.get("userinfo");
            user.setUsername(userInfo.get("code").asText());
            user.setRealName(userInfo.get("name").asText());
            user.setSsoId(userInfo.get("id").asLong());
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
        return user;
    }

    @Override
    public ResultData<String> getToken(String url) {
        String data = restTemplate.postForObject(url, HttpEntity.class, String.class);
        UserInfo userInfo = parseResponseData(data);

        if(userInfoService.getByUsername(userInfo.getUsername()) == null){
            userInfoService.save(userInfo);
        }
        UserDetails userDetails = userInfoService.loadUserByUsername(userInfo.getUsername());
        Map<String, Object> map = (Map<String, Object>) JSON.parseObject(String.valueOf(JSONObject.parse(JSONObject.toJSONString(userDetails))), Map.class);
        String token = JWTUtil.createToken(map,new byte[123]);
        return ResultData.success(token);
    }

}
