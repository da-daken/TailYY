package com.tailYY.backend.common.request.commodity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/14
 **/
@Data
public class CommodityRequest implements Serializable {

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
    private String status = "1";

    /**
     * 过期时间（指这个商品的售后过期时间）
     */
    private Date expireTime = new Date();

}
