package com.tailYY.backend.common.request.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/16
 **/
@Data
public class CreateOrderRequest implements Serializable {
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
     * 订单类型 0线上 1线下
     */
    private String orderType;

}
