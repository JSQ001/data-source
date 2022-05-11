package com.eicas.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.entity.UserInfo;
import com.eicas.service.UserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@Tag(name = "DataTypeController", description = "数据类型接口")
public class UserController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/resource")
    public Mono<UserInfo> getUserResource() {
        return Mono.just(userInfoService.getUserResource());
    }

    @GetMapping("/list")
    public Mono<Page<UserInfo>> listUser( String keyword,
                                    @RequestParam(value = "current", defaultValue = "1") Integer current,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return Mono.just(userInfoService.listUser(keyword, Page.of(current, size)));
    }

    @GetMapping("/list/by/dataType")
    public List<UserInfo> listUserByDataType(@RequestParam Long dataTypeId) {
        return userInfoService.listUserByDataType(dataTypeId);
    }
}