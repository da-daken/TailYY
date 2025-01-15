package com.tailYY.backend.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 宠物用品 & 宠物服务
 * @TableName commodity
 */
@TableName(value ="commodity")
@Data
public class Commodity implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String commodity;

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
     * 商品类型 0 宠物用品 1 宠物服务
     */
    private String commodityType;

    /**
     * 商品种类id
     */
    private Integer classId;

    /**
     * 库存 宠物服务没有库存
     */
    private Integer stockCount;

    /**
     * 最低库存提醒（宠物服务没有）
     */
    private Integer stockRemind;

    /**
     * 状态 0 正常， 1 关闭
     */
    private String status;

    /**
     * 评价列表
     */
    private String comments;

    /**
     * 过期时间（指这个商品的售后过期时间）
     * 单位为小时
     */
    private Integer expireTime;

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