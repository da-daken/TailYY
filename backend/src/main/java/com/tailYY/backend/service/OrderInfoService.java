package com.tailYY.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tailYY.backend.common.request.order.*;
import com.tailYY.backend.model.OrderInfo;
import com.tailYY.backend.model.Vo.OrderVo;

import java.text.ParseException;
import java.util.List;

/**
* @author 28447
* @description 针对表【order_info(订单)】的数据库操作Service
* @createDate 2025-01-11 16:22:24
*/
public interface OrderInfoService extends IService<OrderInfo> {

    Boolean payOrder(PayOrderRequest request);

    Long createOnlineOrder(CreateOnlineOrderRequest request);

    Long createOfflineOrder(CreateOfflineRequest request);

    List<OrderVo> getOrderList(QueryOrderRequest request);

    Boolean sendOrder(Long orderId);

    Boolean signOrder(Long orderId);

    Boolean commitOrder( CommitOrderRequest request);

    Boolean refundOrder(ApplyRequest request) throws ParseException;

    Boolean handleApply(ApplyRequest request);

    Boolean cancelOrder(String orderId);
}
