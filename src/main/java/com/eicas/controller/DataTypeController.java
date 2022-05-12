package com.eicas.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.common.ResultData;
import com.eicas.entity.DataType;
import com.eicas.service.DataTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/dataType")
@Tag(name = "DataTypeController", description = "数据类型接口")
public class DataTypeController {

    @Resource
    private DataTypeService dataTypeService;

    @GetMapping("/user")
    public Mono<String> getUser() {
        return Mono.just("1111");
    }

    @Operation(summary = "创建数据类型",description = "")
    @PostMapping(value = "/create")
    public ResultData<Boolean> createDataType(@RequestBody DataType param) {
        return dataTypeService.create(param);
    }

    @Operation(summary = "更新数据类型",description = "")
    @PostMapping(value = "/update")
    public boolean updateDataType(@RequestBody DataType param) {
        return dataTypeService.saveOrUpdate(param);
    }

    @Operation(summary = "分配可访问用户",description = "")
    @PostMapping(value = "/malloc/user")
    public ResultData<Boolean> mallocUser(@RequestParam Long dataTypeId, @RequestBody List<Long> userIds) {
        return dataTypeService.mallocUser(dataTypeId, userIds);
    }


    @Operation(summary="根据用户id分页查询数据资源树")
    @GetMapping(value = "/list/tree/page/{userId}")
    public ResultData<Page<DataType>> listDataTypeTreeByUserId(
            @PathVariable Long userId,
            String keyword,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return dataTypeService.listDataTypeTreeByParam(userId,keyword,Page.of(current,size));
    }

    @Operation(summary="查询数据类型树")
    @GetMapping(value = "/list/tree/{userId}")
    public ResultData<List<DataType>> listTree(@PathVariable Long userId) {
        return dataTypeService.listTree(userId);
    }

    @Operation(summary = "删除数据类型")
    @PostMapping(value = "/delete/{id}")
    public ResultData<Boolean> delete(@PathVariable Long id) {
        return dataTypeService.removeById(id);
    }

}