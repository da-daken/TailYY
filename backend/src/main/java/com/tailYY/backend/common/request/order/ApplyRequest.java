package com.tailYY.backend.common.request.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/19
 **/
@Data
public class ApplyRequest implements Serializable {

    private Long orderId;

    /**
     * true -> 申请退货
     * false -> 取消退货
     */
    private Boolean apply;

    /**
     * 0 -> 退货
     * 1 -> 换货
     */
    private String cancelOrRefund;
}
