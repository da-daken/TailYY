package com.tailYY.backend.common.request.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/16
 **/
@Data
public class PayOrderRequest implements Serializable {
    /**
     * 订单号
     */
    private Long id;

    /**
     * 用户id
     */
    private Integer userId;

}
