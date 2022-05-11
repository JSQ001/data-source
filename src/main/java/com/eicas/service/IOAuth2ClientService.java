package com.eicas.service;

import com.eicas.common.ResultData;
import com.eicas.entity.UserInfo;

public interface IOAuth2ClientService {

    UserInfo parseResponseData(String data);

    ResultData<String> getToken(String url);
}
