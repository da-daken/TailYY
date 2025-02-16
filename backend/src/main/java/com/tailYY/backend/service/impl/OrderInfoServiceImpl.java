package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tailYY.backend.common.constants.CommonConstants;
import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.constants.GoodsEnum;
import com.tailYY.backend.common.constants.OrderStatusEnum;
import com.tailYY.backend.common.exception.BusinessException;
import com.tailYY.backend.common.request.order.*;
import com.tailYY.backend.common.util.BeanCopyUtils;
import com.tailYY.backend.common.util.OrderGenerator;
import com.tailYY.backend.mapper.OrderInfoMapper;
import com.tailYY.backend.model.Class;
import com.tailYY.backend.model.Commodity;
import com.tailYY.backend.model.Do.UserDo;
import com.tailYY.backend.model.OrderInfo;
import com.tailYY.backend.model.Pet;
import com.tailYY.backend.model.Vo.OrderVo;
import com.tailYY.backend.model.json.Comments;
import com.tailYY.backend.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Resource
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${order.expire.minutes}")
    private Long expireMinutes;

    private static HashMap<Integer, IService> serviceMap = new HashMap<>();

    private static final String ORDER_CANCEL_KEY_PREFIX = "order:cancel:";

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
    @Transactional
    public Long createOnlineOrder(CreateOnlineOrderRequest request) {
        OrderInfo orderInfo = BeanCopyUtils.copyBean(request, OrderInfo.class);
        // 线上
        orderInfo.setOrderType(CommonConstants.ONLINE);
        return createOrder(orderInfo, request.getClassType());
    }

    @Override
    @Transactional
    public Long createOfflineOrder(CreateOfflineRequest request) {
        OrderInfo orderInfo = BeanCopyUtils.copyBean(request, OrderInfo.class);
        // 线下
        orderInfo.setOrderType(CommonConstants.OFFLINE);
        return createOrder(orderInfo, request.getClassType());
    }

    @Override
    public List<OrderVo> getOrderList(QueryOrderRequest request) {
        OrderInfo order = BeanCopyUtils.copyBean(request, OrderInfo.class);
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>(order);
        List<OrderInfo> orderInfos = list(queryWrapper);
        if (orderInfos.isEmpty()) {
            return Collections.emptyList();
        }
        // 提取所有唯一类别ID
        Set<Integer> classIds = orderInfos.stream()
                .map(OrderInfo::getClassId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // 获取类别
        List<Class> classList = classService.getAllClass(classIds);
        HashMap<Long, HashMap<String, String>> classMap = new HashMap<>();
        for (Class aClass : classList) {
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("className", aClass.getClassName());
            stringStringHashMap.put("classType", aClass.getClassType());
            classMap.put(aClass.getId(), stringStringHashMap);
        }

        // 提取所有userId
        Set<Integer> userIds = orderInfos.stream()
                .flatMap(orderInfo -> Stream.of(
                            orderInfo.getUserId(),
                            orderInfo.getOperateId()
                        ))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // 获取username
        List<UserDo> userDoList = userService.getAllUserName(userIds);
        HashMap<Long, String> userIdMap = new HashMap<>();
        for (UserDo userDo : userDoList) {
            userIdMap.put(userDo.getId(), userDo.getUsername());
        }

        // 获取商品名称
        HashMap<Integer, String> goodsNameMap = new HashMap<>();
        // 获取宠物名称
        HashMap<Integer, String> petNameMap = new HashMap<>();

        List<OrderVo> orderVoList = orderInfos.stream()
                .map(orderInfo -> {
                    OrderVo orderVo = BeanCopyUtils.copyBean(orderInfo, OrderVo.class);
                    orderVo.setClassName(String.valueOf(classMap.get(Long.valueOf(orderInfo.getClassId())).get("className")));
                    orderVo.setClassType(String.valueOf(classMap.get(Long.valueOf(orderInfo.getClassId())).get("classType")));
                    orderVo.setUsername(String.valueOf(userIdMap.get(Long.valueOf(orderInfo.getUserId()))));
                    orderVo.setOperateName(String.valueOf(userIdMap.get(Long.valueOf(orderInfo.getUserId()))));
                    if (StringUtils.equals(classMap.get(Long.valueOf(orderInfo.getClassId())).get("classType"), GoodsEnum.PET.getCode())) {
                        if (petNameMap.get(orderInfo.getGoodsId()) == null) {
                            petNameMap.put(orderInfo.getGoodsId(), petService.getById(orderInfo.getGoodsId()).getPetname());
                        }
                        orderVo.setGoodsName(petNameMap.get(orderInfo.getGoodsId()));
                    } else {
                        if (goodsNameMap.get(orderInfo.getGoodsId()) == null) {
                            goodsNameMap.put(orderInfo.getGoodsId(), commodityService.getById(orderInfo.getGoodsId()).getCommodity());
                        }
                        orderVo.setGoodsName(goodsNameMap.get(orderInfo.getGoodsId()));
                    }
                    return orderVo;
                }).collect(Collectors.toList());
        return orderVoList;
    }

    @Override
    public Boolean sendOrder(Long orderId) {
        OrderInfo order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "订单不存在");
        }
        if (!OrderStatusEnum.WAIT_SEND.getCode().equals(order.getCurStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "订单状态不是待发货");
        }
        order.setPreStatus(order.getCurStatus());
        order.setCurStatus(OrderStatusEnum.WAIT_SIGN.getCode());
        return updateById(order);
    }

    @Override
    public Boolean signOrder(Long orderId) {
        OrderInfo order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "订单不存在");
        }
        if (!OrderStatusEnum.WAIT_SIGN.getCode().equals(order.getCurStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "订单状态不是待签收");
        }
        order.setPreStatus(order.getCurStatus());
        order.setCurStatus(OrderStatusEnum.WAIT_COMMENT.getCode());
        return updateById(order);
    }

    @Override
    public Boolean commitOrder(CommitOrderRequest request) {
        OrderInfo order = getById(request.getOrderId());
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "订单不存在");
        }
        if (!OrderStatusEnum.WAIT_COMMENT.getCode().equals(order.getCurStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "订单状态不是待评价");
        }
        Comments comments = new Comments();
        comments.setIsBuyer(true);
        comments.setUserId(request.getUserId());
        comments.setComment(request.getComment());
        // 保存订单
        order.setComments(request.getComment());
        order.setPreStatus(order.getPreStatus());
        order.setCurStatus(OrderStatusEnum.SUCCESS.getCode());
        updateById(order);
        // 同步商品评论
        commodityService.addOrderComments(order.getGoodsId(), comments);
        return true;
    }

    @Override
    public Boolean refundOrder(ApplyRequest request) throws ParseException {
        OrderInfo order = getById(request.getOrderId());
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "订单不存在");
        }
        if (!OrderStatusEnum.WAIT_PAY.getCode().equals(order.getCurStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前订单状态还未支付");
        }

        // 判断用户动作 为取消退货
        if (!request.getApply() && order.getCurStatus().equals(OrderStatusEnum.APPLYING.getCode())) {
            order.setCurStatus(order.getCurStatus());
            return updateById(order);
        }
        // 获取当前时间
        Date now = new Date();
        // 判断当前时间是否在特定时间之前
        if (!now.before(order.getServiceTime())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前订单售后时间已经过期，不允许退货");
        }
        order.setPreStatus(order.getCurStatus());
        order.setCurStatus(OrderStatusEnum.APPLYING.getCode());
        order.setCancelOrRefund(request.getCancelOrRefund());
        return updateById(order);
    }

    @Override
    public Boolean handleApply(ApplyRequest request) {
        OrderInfo order = getById(request.getOrderId());
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "订单不存在");
        }
        if (!order.getCurStatus().equals(OrderStatusEnum.APPLYING.getCode())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前订单状态未在申请中");
        }
        // 同意申请
        if (request.getApply()) {
            if (order.getCancelOrRefund().equals("0")) {
                order.setCurStatus(OrderStatusEnum.CANCEL.getCode());
            } else if (order.getCancelOrRefund().equals("1")) {
                order.setCurStatus(OrderStatusEnum.WAIT_SEND.getCode());
            }
            order.setReqStatus("1");
        } else {
            // 驳回申请
            order.setCurStatus(order.getPreStatus());
            order.setReqStatus("0");
        }
        return updateById(order);
    }

    @Override
    public Boolean cancelOrder(Long orderId) {
        OrderInfo order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "订单不存在");
        }
        if (!order.getCurStatus().equals(OrderStatusEnum.WAIT_PAY.getCode())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前订单状态未在待支付中");
        }
        order.setPreStatus(order.getCurStatus());
        order.setCurStatus(OrderStatusEnum.CANCEL.getCode());
        return updateById(order);
    }

    @Transactional
    public Long createOrder(OrderInfo order, String classType) {
        if (!StringUtils.equals(classType, GoodsEnum.PET.getCode())) {
            Commodity commodity = commodityService.getByIdLock(order.getGoodsId());
            if (CommonConstants.OFFLINE.equals(commodity.getStatus())) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "商品售卖状态异常");
            }
            Integer stockCount = commodity.getStockCount();
            // 判断库存是否够
            if (order.getCount() > stockCount) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "库存不足");
            }
            // 减库存
            commodity.setStockCount(stockCount - order.getCount());
            commodityService.updateById(commodity);
            // 获取售后服务截止时间 宠物无售后
            Integer expireTime = commodity.getExpireTime();
            order.setServiceTime(OrderGenerator.calculateExpirationTime(expireTime));
            // 订单价格
            order.setPrice(order.getCount() * commodity.getPrice());
            // 类别id
            order.setClassId(commodity.getClassId());
        } else {
            Pet pet = petService.getById(order.getGoodsId());
            if (CommonConstants.OFFLINE.equals(String.valueOf(pet.getStatus()))) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "宠物售卖状态异常");
            }
            // 出售状态
            pet.setStatus(1);
            petService.updateById(pet);
            order.setPrice(pet.getPrice());
            // 类别id
            order.setClassId(Integer.valueOf(String.valueOf(pet.getClassId())));
        }

        // 生成订单号
        Long orderNo = OrderGenerator.generateOrderId(order.getUserId());
        order.setId(orderNo);
        // 状态为待支付
        order.setCurStatus(OrderStatusEnum.WAIT_PAY.getCode());

        save(order);
        // 设置Redis中的键和过期时间
        // 自动取消订单
        redisTemplate.opsForValue().set(ORDER_CANCEL_KEY_PREFIX + orderNo, "pending", expireMinutes, java.util.concurrent.TimeUnit.SECONDS);
        return orderNo;
    }

    /**
     *
     * @return 订单号
     */
    @Override
    public Boolean payOrder(PayOrderRequest request) {
        OrderInfo order = getById(request.getId());
        if (order == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "订单不存在");
        }
        // 确认是本人的订单
        if (order.getUserId() != request.getUserId()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "不是本人订单无法进行支付");
        }
        // 查询订单状态
        if (!OrderStatusEnum.WAIT_PAY.getCode().equals(order.getCurStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请检查订单状态是否为待支付");
        }
        // 查询商品状态? 应该不用查询商品状态了，创单完成就已经锁单了

        // 支付改变订单状态为 待发货
        order.setPreStatus(order.getCurStatus());
        order.setCurStatus(OrderStatusEnum.WAIT_SEND.getCode());

        boolean update = updateById(order);
        // 删除redis的定时器
        redisTemplate.delete(ORDER_CANCEL_KEY_PREFIX + order.getId());
        return update;
    }
}




