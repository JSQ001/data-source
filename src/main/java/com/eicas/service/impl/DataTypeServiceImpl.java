package com.eicas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.common.ResultData;
import com.eicas.entity.DataType;
import com.eicas.entity.UserDataTypeR;
import com.eicas.entity.UserInfo;
import com.eicas.service.DataTypeService;
import com.eicas.mapper.DataTypeMapper;
import com.eicas.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author jsq
* @description 针对表【data_type(数据分类表)】的数据库操作Service实现
* @createDate 2022-05-09 22:54:48
*/
@Transactional
@Service
public class DataTypeServiceImpl extends ServiceImpl<DataTypeMapper, DataType> implements DataTypeService{

    @Resource
    private DataTypeMapper dataTypeMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserDataTypeRServiceImpl userDataTypeRService;
    @Override
    public boolean create(DataType dataType) {
        dataType.setLogicalCode(getLogicalCode(dataType.getParentId()));
        return this.save(dataType);
    }

    @Override
    public ResultData<Page<DataType>> listDataTypeTreeByParam(Long userId, String keyword, Page<DataType> page) {
        UserInfo userInfo = userInfoService.getById(userId);
        if(userInfo == null){
            return ResultData.failed("不存在的用户ID："+userId);
        }

        if(userInfo.getUsername().equals("admin")){
            if(StringUtils.hasText(keyword)) {
                Set<String> logicalCodeSet = new HashSet<>();

                List<DataType> list = list(new LambdaQueryWrapper<DataType>()
                        .like(DataType::getLogicalCode, keyword.trim())
                        .or()
                        .like(DataType::getName, keyword.trim())
                );
                if(list.size() > 0){
                    //查找所有匹配的code，包含上级code
                    list.forEach(item-> {
                        String logicalCodeStr =  item.getLogicalCode();
                        for(int i = logicalCodeStr.length() ; i >= 0; i-=2){
                            logicalCodeSet.add(logicalCodeStr.substring(0,i));
                        }
                    });
                    //查询所有匹配的节点树
                    page = dataTypeMapper.getTreeByLogicalCodes(logicalCodeSet,page);
                    page.setRecords(filter(page.getRecords(), logicalCodeSet));
                }

            }else {
               return ResultData.success(dataTypeMapper.listDataTypeTreePage(page));
            }
        }else {
            return ResultData.success(dataTypeMapper.listUserDataTypeByKeyword(userId, keyword, page));
        }

        return ResultData.success(page);
    }


    @Override
    public ResultData<List<DataType>> listTree(Long userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        if(userInfo == null){
            return ResultData.failed("不存在的用户ID："+userId);
        }
        if(userInfo.getUsername().equals("admin")){
            return ResultData.success(dataTypeMapper.getChildrenByParentId(null));
        }else {
            return ResultData.success(dataTypeMapper.getUserDataType(userId));
        }
    }

    @Override
    public ResultData<Boolean> mallocUser(Long dataTypeId, List<Long> userIds) {
        userDataTypeRService.remove(new LambdaQueryWrapper<UserDataTypeR>().eq(UserDataTypeR::getDataTypeId,dataTypeId));
        List<UserDataTypeR> list = userIds.stream().map(
                i-> new UserDataTypeR()
                        .setDataTypeId(dataTypeId)
                        .setUserId(i)
        ).collect(Collectors.toList());
        if(!userDataTypeRService.saveBatch(list)){
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultData.failed("保存失败");
        }
        return ResultData.success(true,"保存成功");
    }


    private String getLogicalCode(Long parentId){
        String maxEncode = dataTypeMapper.getMaxLogicalCodeByParentId(parentId);
        if(parentId == null || parentId == 0){
            return String.valueOf(Integer.parseInt(maxEncode) + 1);
        }else {
            String[] codeList = maxEncode.split("-");
            String lastCode = codeList[codeList.length-1];
            if(lastCode.equals("0")){
                DataType parent = getById(parentId);
                return parent.getCode() + "-" + 1;
            }else {
                codeList[codeList.length-1] = String.valueOf(Integer.parseInt(codeList[codeList.length-1]) + 1);
                return String.join("-", codeList);
            }
        }
    }

    /**
     * 递归去重
     * */
    List<DataType> filter(List<DataType> target, Set<String> logicalCodeSet){
        if(target.size()>0){
            target = target.stream().filter(
                    i-> logicalCodeSet.contains(i.getLogicalCode())
            ).collect(Collectors.toList());
            target.forEach(i->{
                if(i.getChildren() != null && !i.getChildren().isEmpty()){
                    i.setChildren(filter(i.getChildren(), logicalCodeSet));
                }
            });
        }
        return target;
    }


}




