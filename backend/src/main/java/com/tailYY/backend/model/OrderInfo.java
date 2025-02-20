package com.tailYY.backend.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 订单
 * @TableName order_info
 */
@TableName(value ="order_info")
@Data
public class OrderInfo implements Serializable {
    /**
     * 订单号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id（宠物或者商品）
     */
    private Integer goodsId;

    /**
     * 类别id 0 宠物用品，1 宠物服务，2 宠物
     */
    private Integer classId;

    /**
     * 操作者id（店员或者店长）
     */
    private Integer operateId;

    /**
     * 订单地址
     */
    private String address;

    /**
     * 订单价格
     */
    private Double price;

    /**
     * 订单数量
     */
    private Integer count;

    /**
     * 订单类型 0线上 1线下
     */
    private String orderType;

    /**
     * 订单评价(会同步到对应的商品表中)
     */
    private String comments;

    /**
     * 当前订单状态
     */
    private String curStatus;

    /**
     * 订单上一个状态
     */
    private String preStatus;

    /**
     * 退换货状态
     */
    private String reqStatus;

    /**
     * 是退货还是换货
     */
    private String cancelOrRefund;

    /**
     * 售后服务截止时间
     */
    private Date serviceTime;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}