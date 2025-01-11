package com.tailYY.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tailYY.backend.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 28447
* @description 针对表【order_info(订单)】的数据库操作Mapper
* @createDate 2025-01-11 16:22:24
* @Entity generator.domain.OrderInfo
*/
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}




