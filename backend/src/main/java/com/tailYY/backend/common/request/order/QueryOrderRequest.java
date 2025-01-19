package com.tailYY.backend.common.request.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/19
 **/
@Data
public class QueryOrderRequest implements Serializable {
    /**
     * 订单号
     */
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
     * 订单类型 0线上 1线下
     */
    private String orderType;

    /**
     * 当前订单状态
     */
    private String curStatus;

    /**
     * 退换货状态
     */
    private String reqStatus;

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
