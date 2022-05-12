package com.eicas.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * 数据分类表
 * @TableName data_type
 */
@TableName(value ="data_type")
@Data
public class DataType implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 数据分类编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 数据分类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 上级id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 树形结构层级关系
     */
    @TableField(value = "logical_code")
    private String logicalCode;
    /**
     * 链接url
     */
    @TableField(value = "url")
    private String url;

    @TableField(value = "sort_order")
    private Integer sortOrder;
    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 更新人
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<DataType> children;
}