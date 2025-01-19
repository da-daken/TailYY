package com.tailYY.backend.model.Vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/19
 **/
@Data
public class OrderVo implements Serializable {
    /**
     * 订单号
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 商品名字（宠物或者商品）
     */
    private String goodsName;

    /**
     * 类别id 0 宠物用品，1 宠物服务，2 宠物
     */
    private String className;

    /**
     * 操作者name（店员或者店长）
     */
    private String operateName;

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
     * 售后服务截止时间
     */
    private Date serviceTime;

    /**
     * 下单时间
     */
    private Date createtime;

    /**
     * 修改时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

}
