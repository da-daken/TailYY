package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tailYY.backend.mapper.OrderInfoMapper;
import com.tailYY.backend.model.OrderInfo;
import com.tailYY.backend.service.OrderInfoService;
import org.springframework.stereotype.Service;

/**
* @author 28447
* @description 针对表【order_info(订单)】的数据库操作Service实现
* @createDate 2025-01-11 16:22:24
*/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

}




