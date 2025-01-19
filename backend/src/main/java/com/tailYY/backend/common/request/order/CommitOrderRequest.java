package com.tailYY.backend.common.request.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/19
 **/
@Data
public class CommitOrderRequest implements Serializable {

    private Long orderId;

    private Long userId;
    private String comment;
}
