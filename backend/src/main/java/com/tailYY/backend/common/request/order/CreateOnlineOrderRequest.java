package com.tailYY.backend.common.request.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/16
 **/
@Data
public class CreateOnlineOrderRequest implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id（宠物或者商品）
     */
    private Integer goodsId;

    /**
     * 类别id
     */
    private Integer classId;

    /**
     * 订单地址
     */
    private String address;

    /**
     * 订单数量
     */
    private Integer count;

}
