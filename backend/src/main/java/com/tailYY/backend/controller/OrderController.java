package com.tailYY.backend.controller;

import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.request.order.CreateOrderRequest;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.ResultUtils;
import com.tailYY.backend.model.OrderInfo;
import com.tailYY.backend.service.OrderInfoService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author daken 2025/1/15
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderInfoService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/createOrder")
    public BaseResponse<Boolean> createOrder(@RequestBody CreateOrderRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Assert.isNull(request.getUserId(), "userId is null");
        Assert.isNull(request.getOrderType(), "orderType is null");
        Assert.isNull(request.getAddress(), "address is null");
        Assert.isNull(request.getGoodsId(), "goodsId is null");
        Assert.isNull(request.getOperateId(), "operateId is null");

        return ResultUtils.success(orderService.createOrder(request));
    }
}
