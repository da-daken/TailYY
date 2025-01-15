package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tailYY.backend.common.constants.GoodsEnum;
import com.tailYY.backend.common.constants.OrderStatusEnum;
import com.tailYY.backend.common.request.order.CreateOrderRequest;
import com.tailYY.backend.common.util.BeanCopyUtils;
import com.tailYY.backend.common.util.OrderGenerator;
import com.tailYY.backend.mapper.OrderInfoMapper;
import com.tailYY.backend.model.Class;
import com.tailYY.backend.model.Commodity;
import com.tailYY.backend.model.OrderInfo;
import com.tailYY.backend.service.ClassService;
import com.tailYY.backend.service.CommodityService;
import com.tailYY.backend.service.OrderInfoService;
import com.tailYY.backend.service.PetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
* @author 28447
* @description 针对表【order_info(订单)】的数据库操作Service实现
* @createDate 2025-01-11 16:22:24
*/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService, ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private PetService petService;

    @Resource
    private CommodityService commodityService;

    @Resource
    private ClassService classService;

    private static HashMap<Integer, IService> serviceMap = new HashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 确保这是根应用上下文（避免重复初始化）
        if (event.getApplicationContext().getParent() == null) {
            serviceMap.put(0, commodityService);
            serviceMap.put(1, commodityService);
            serviceMap.put(2, petService);
        }
    }

    @Override
    public Boolean createOrder(CreateOrderRequest request) {
        OrderInfo order = BeanCopyUtils.copyBean(request, OrderInfo.class);
        // 生成订单号
        Long orderNo = OrderGenerator.generateOrderId(request.getUserId());
        order.setId(orderNo);

        // 状态为待支付
        order.setCurStatus(OrderStatusEnum.WAIT_PAY.getCode());

        // 获取售后服务截止时间 宠物无售后
        Class classDO = classService.getById(request.getClassId());
        if (!StringUtils.equals(classDO.getClassType(), GoodsEnum.PET.getCode())) {
            Commodity commodity = commodityService.getById(request.getGoodsId());
            Integer expireTime = commodity.getExpireTime();
            order.setServiceTime(OrderGenerator.calculateExpirationTime(expireTime));
        }
        return save(order);
    }



}




