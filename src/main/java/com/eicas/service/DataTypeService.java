package com.eicas.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.common.ResultData;
import com.eicas.entity.DataType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jsq
* @description 针对表【data_type(数据分类表)】的数据库操作Service
* @createDate 2022-05-09 22:54:48
*/
public interface DataTypeService extends IService<DataType> {
    ResultData<Boolean> create(DataType dataType);

    ResultData<Page<DataType>> listDataTypeTreeByParam(Long userId, String keyword, Page<DataType> page);

    ResultData<List<DataType>> listTree(Long userId);
    ResultData<Boolean> mallocUser(Long dataTypeId, List<Long> userIds);
    ResultData<Boolean> removeById(Long id);
}
