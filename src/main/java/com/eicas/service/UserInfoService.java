package com.eicas.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.entity.DataType;
import com.eicas.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
* @author jsq
* @description 针对表【user_info(用户信息表)】的数据库操作Service
* @createDate 2022-05-09 22:55:11
*/
public interface UserInfoService extends IService<UserInfo>, UserDetailsService {

    UserInfo getByUsername(String username);
    UserInfo getUserResource();
    Page<UserInfo> listUser(String keyword, Page<UserInfo> page);
    List<UserInfo> listUserByDataType(Long dataTypeId);
}
