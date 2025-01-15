package com.tailYY.backend.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 宠物
 * @TableName pet
 */
@TableName(value ="pet")
@Data
public class Pet implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 宠物名称
     */
    private String petname;

    /**
     * 宠物种类
     */
    private Integer classId;

    /**
     * 宠物信息
     */
    private String info;

    /**
     * 宠物照片
     */
    private String pic;

    /**
     * 价格
     */
    private Double price;

    /**
     * 健康状态记录
     */
    private String bodyRecord;

    /**
     * 服务记录
     */
    private String serviceRecord;

    /**
     * 笼子号
     */
    private Integer zoneId;

    /**
     * 状态 0在售 1已售
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}