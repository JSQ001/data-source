package com.eicas.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.entity.DataType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

/**
* @author jsq
* @description 针对表【data_type(数据分类表)】的数据库操作Mapper
* @createDate 2022-05-09 22:54:48
* @Entity com.eicas.entity.DataType
*/
@Mapper
public interface DataTypeMapper extends BaseMapper<DataType> {

    /**
     * 根据id获取子级节点最大code值
     * */
    @Select("<script>" +
            "select ifnull((" +
            "   select Max(logical_code) logical_code from data_type " +
            "   <where>" +
            "       <choose>" +
            "           <when test='id != \"\" and id != null '>" +
            "               and parent_id = #{id}" +
            "           </when>" +
            "       <otherwise>" +
            "           and parent_id = 0 or parent_id is null" +
            "       </otherwise>" +
            "       </choose>" +
            "   </where>" +
            "),0) as code\n" +
            "</script>")
    String getMaxLogicalCodeByParentId(Long id);

    /**
     * 查询指定id列
     * */
    @Select("" +
            "<script>" +
            "select id, code, name, url, sort_order as sortOrder, parent_id as parentId,logical_code as logicalCode  \n" +
            "from data_type\n" +
            "<where>" +
            "   is_deleted = 0 and parent_id = 0 or parent_id is null or parent_id = ''" +
            "   <when test='logicalCodes != null ' >" +
            "       and logical_code in" +
            "       <foreach collection=\"logicalCodes\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">\n" +
            "           #{item}\n" +
            "       </foreach>" +
            "   </when>" +
            "</where> " +
            "</script>" +
            "")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "id", property = "children", many = @Many(
                    select = "com.eicas.mapper.DataTypeMapper.getChildrenByParentId", fetchType = FetchType.EAGER
            ))
    })
    List<DataType> listDataTypeByCodes(Set<String> logicalCodes);


    /**
     * 查询指定id列表的栏目树
     * */
    @Select("" +
            "<script>" +
            "select id, code, name, url, sort_order as sortOrder, parent_id as parentId,logical_code as logicalCode \n" +
            "from data_type\n" +
            "<where>" +
            "   is_deleted = 0 and parent_id is null or parent_id = '' or parent_id = 0" +
            "</where>" +
            "</script>" +
            "")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "id", property = "children", many = @Many(
                    select = "com.eicas.mapper.DataTypeMapper.getChildrenByParentId", fetchType = FetchType.EAGER
            ))
    })
    Page<DataType> listDataTypeTreePage(Page<DataType> page);

    /**
     * 查询指定id列表的栏目树
     * */
    @Select("" +
            "<script>" +
            "select id, code, name, url, sort_order as sortOrder, parent_id as parentId,logical_code as logicalCode \n" +
            "from data_type\n" +
            "<where>" +
            "   is_deleted = 0 and parent_id is null or parent_id = '' or parent_id = 0" +
            "</where>" +
            "</script>" +
            "")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "id", property = "children", many = @Many(
                    select = "com.eicas.mapper.DataTypeMapper.getChildrenByParentId", fetchType = FetchType.EAGER
            ))
    })
    List<DataType> listDataTypeTree();


    @Select("" +
            "<script>" +
            "select id, code, name, url, sort_order as sortOrder, parent_id as parentId,logical_code as logicalCode \n" +
            "from data_type\n" +
            "<where>" +
            "   is_deleted = 0" +
            "   <choose>" +
            "       <when test='parentId != \"\" and parentId !=null and parentId != 0 '> " +
            "           and parent_id = #{parentId}" +
            "       </when>" +
            "       <otherwise>" +
            "           and parent_id is null or parent_id = '' or parent_id = 0" +
            "       </otherwise>" +
            "   </choose>" +
            "</where>" +
            "</script>" +
            "")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "id", property = "children", many = @Many(
                    select = "com.eicas.mapper.DataTypeMapper.getChildrenByParentId", fetchType = FetchType.EAGER
            ))
    })
    List<DataType> getChildrenByParentId(Long parentId);


    @Select("" +
            "<script>" +
            "select id, code, name, url, sort_order as sortOrder, logical_code, parent_id\n" +
            "from data_type as t, user_data_type_r as r\n" +
            "where t.is_deleted = 0 \n" +
            "and r.user_id = #{userId} " +
            "</script>" +
            "")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "id", property = "children", many = @Many(
                    select = "com.eicas.mapper.DataTypeMapper.getChildrenByParentId", fetchType = FetchType.EAGER
            ))
    })
    List<DataType> getUserDataType(Long userId);



    @Select("" +
            "<script>" +
            "select id, code, name, url, sort_order as sortOrder, logical_code, parent_id\n" +
            "from data_type as t, user_data_type_r as r\n" +
            "<where>" +
            "   t.is_deleted = 0 \n" +
            "   and r.user_id = #{userId} " +
            "   <when test='keyword != \"\" and keyword !=null '> " +
            "      and code like CONCAT('%',#{keyword},'%')" +
            "       or name like CONCAT('%',#{keyword},'%')" +
            "   </when>" +
            "</where>" +
            "</script>" +
            "")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "id", property = "children", many = @Many(
                    select = "com.eicas.mapper.DataTypeMapper.getChildrenByParentId", fetchType = FetchType.EAGER
            ))
    })
    Page<DataType> listUserDataTypeByKeyword(Long userId, String keyword,Page<DataType> page);

}




