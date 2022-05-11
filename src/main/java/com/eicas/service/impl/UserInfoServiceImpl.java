package com.eicas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.entity.UserInfo;
import com.eicas.service.UserInfoService;
import com.eicas.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author jsq
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2022-05-09 22:55:11
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService{

    @Value("${user.defaultPwd}")
    private String defaultPwd;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //UserInfo userInfo = getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, username));

        return new User(username, passwordEncoder.encode(defaultPwd), new ArrayList<>());
    }

    @Override
    public UserInfo getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, username));
    }

    @Override
    public UserInfo getUserResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getByUsername(String.valueOf(authentication.getPrincipal()));
    }

    @Override
    public Page<UserInfo> listUser(String keyword, Page<UserInfo> page) {
        LambdaQueryWrapper<UserInfo> wrapper =  new LambdaQueryWrapper<>();
        if(StringUtils.hasText(keyword)){
            wrapper.like(UserInfo::getUsername, keyword)
                    .or()
                    .like(UserInfo::getRealName, keyword);
        }
        wrapper.eq(UserInfo::getDeleted, false);
        return page(page, wrapper);
    }

    @Override
    public List<UserInfo> listUserByDataType(Long dataTypeId) {
        return userInfoMapper.listUserByDataType(dataTypeId);
    }
}




