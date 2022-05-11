package com.eicas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户数据分类关联表
 * @TableName user_data_type_r
 */
@TableName(value ="user_data_type_r")
@Data
@Accessors(chain = true)
public class UserDataTypeR implements Serializable {
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 数据分类id
     */
    @TableField(value = "data_type_id")
    private Long dataTypeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}