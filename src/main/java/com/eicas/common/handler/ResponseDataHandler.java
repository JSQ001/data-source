package com.eicas.common.handler;

import com.eicas.common.ResultData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 统一处理返回值/响应体
 */

@RestControllerAdvice
@Slf4j
public class ResponseDataHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        if (returnType.getDeclaringClass().getName().contains("springfox")) {
            return false;
        }
        System.out.println(!returnType.getGenericParameterType().equals(ResultData.class));
        return !returnType.getGenericParameterType().equals(ResultData.class);
    }

    @Override
    public Object beforeBodyWrite(Object data,
                                  MethodParameter returnType,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(ResultData.success(data));
            } catch (JsonProcessingException e) {

            }
        }
        serverHttpResponse.setStatusCode(HttpStatus.OK);
        return data instanceof ResultData ? data : ResultData.success(data);
    }
}
