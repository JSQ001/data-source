package com.eicas.mapper;

import com.eicas.entity.DataType;
import com.eicas.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author jsq
* @description 针对表【user_info(用户信息表)】的数据库操作Mapper
* @createDate 2022-05-09 22:55:11
* @Entity com.eicas.entity.UserInfo
*/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Select("" +
            "<script>" +
            "    select id, sso_id, username, real_name, phone\n" +
            "    from user_info as u, user_data_type_r as r\n" +
            "    where is_deleted = 0\n" +
            "    and u.id = r.user_id\n" +
            "    and r.data_type_id = #{dataTypeId}" +
            "</script>" +
            "")
    List<UserInfo> listUserByDataType(Long dataTypeId);
}




