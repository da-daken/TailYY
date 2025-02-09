package com.tailYY.backend.controller;

import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.request.order.*;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.ResultUtils;
import com.tailYY.backend.model.OrderInfo;
import com.tailYY.backend.model.Vo.OrderVo;
import com.tailYY.backend.service.OrderInfoService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author daken 2025/1/15
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderInfoService orderService;

    /**
     * 创建线上订单
     */
    @PostMapping("/createOnlineOrder")
    public BaseResponse<Long> createOnlineOrder(@RequestBody CreateOnlineOrderRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Assert.isNull(request.getUserId(), "userId is null");
        Assert.isNull(request.getAddress(), "address is null");
        Assert.isNull(request.getGoodsId(), "goodsId is null");
        Assert.isNull(request.getClassId(), "classId is null");

        return ResultUtils.success(orderService.createOnlineOrder(request));
    }

    /**
     * 创建线下订单
     */
    @PostMapping("/createOfflineOrder")
    public BaseResponse<Long> createOfflineOrder(@RequestBody CreateOfflineRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Assert.isNull(request.getOperateId(), "operateId is null");
        Assert.isNull(request.getClassId(), "classId is null");
        Assert.isNull(request.getGoodsId(), "goodsId is null");
        Assert.isNull(request.getUserId(), "userId is null");
        Assert.isNull(request.getAddress(), "address is null");

        return ResultUtils.success(orderService.createOfflineOrder(request));
    }

    /**
     * 支付订单
     */
    @PostMapping("/payOrder")
    public BaseResponse<Boolean> payOrder(@RequestBody PayOrderRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Assert.isNull(request.getUserId(), "userId is null");
        Assert.isNull(request.getId(), "订单号 is null");

        return ResultUtils.success(orderService.payOrder(request));
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancelOrder")
    public BaseResponse<Boolean> cancelOrder(@RequestBody OrderInfo orderId) {
        if (orderId == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(orderService.cancelOrder(orderId.getId()));
    }

    /**
     * 查询订单
     */
    @GetMapping("/getOrderInfoList")
    public BaseResponse<List<OrderVo>> getOrderInfoList(QueryOrderRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(orderService.getOrderList(request));
    }

    /**
     * 订单发货
     */
    @PostMapping("/sendOrder")
    public BaseResponse<Boolean> sendOrder(@RequestBody OrderInfo orderId) {
        if (orderId == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(orderService.sendOrder(orderId.getId()));
    }

    /**
     * 订单签收
     */
    @PostMapping("/signOrder")
    public BaseResponse<Boolean> signOrder(@RequestBody OrderInfo orderId) {
        if (orderId == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(orderService.signOrder(orderId.getId()));
    }

    /**
     * 评价订单
     */
    @PostMapping("/commentOrder")
    public BaseResponse<Boolean> commitOrder(@RequestBody CommitOrderRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(orderService.commitOrder(request));
    }

    /**
     * 用户申请订单退货
     */
    @PostMapping("/refundApply")
    public BaseResponse<Boolean> refundOrder(@RequestBody ApplyRequest request) throws ParseException {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(orderService.refundOrder(request));
    }

    /**
     * 店员、店长处理退货申请
     */
    @PostMapping("/handleApply")
    public BaseResponse<Boolean> handleOrder(@RequestBody ApplyRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(orderService.handleApply(request));
    }

}
