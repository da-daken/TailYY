package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tailYY.backend.common.constants.CommodityConstants;
import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.exception.BusinessException;
import com.tailYY.backend.common.request.commodity.CommentRequest;
import com.tailYY.backend.common.request.commodity.CommodityRequest;
import com.tailYY.backend.common.util.BeanCopyUtils;
import com.tailYY.backend.common.util.JsonUtils;
import com.tailYY.backend.mapper.CommodityMapper;
import com.tailYY.backend.model.Commodity;
import com.tailYY.backend.model.Vo.CommodityVo;
import com.tailYY.backend.model.json.Comments;
import com.tailYY.backend.service.ClassService;
import com.tailYY.backend.service.CommodityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 28447
* @description 针对表【commodity(宠物用品 & 宠物服务)】的数据库操作Service实现
* @createDate 2025-01-11 16:17:25
*/
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Resource
    private ClassService classService;

    @Override
    public List<CommodityVo> getAndNotify(Commodity commodity) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>(commodity);
        List<Commodity> commodityList = list(queryWrapper);
        // 提取所有唯一类别ID
        Set<Integer> classIds = commodityList.stream()
                .map(Commodity::getClassId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // 获取类别
        HashMap<Integer, String> classMap = classService.getAllClass(classIds);
        return commodityList.stream().map(commodity1 -> {
            CommodityVo commodityVo = BeanCopyUtils.copyBean(commodity1, CommodityVo.class);
            commodityVo.setComments(JsonUtils.convertJsonList(commodity1.getComments(), Comments.class));
            // 将类别转换成名字
            commodityVo.setClassName(classMap.get(commodity1.getClassId()));
            // 为每个商品设置提醒
            // 宠物服务没有
            if (StringUtils.equals(commodity1.getCommodityType(), CommodityConstants.COMMODITY_GOODS) && commodity1.getStockCount() <= commodity1.getStockRemind()) {
                // 将库存不足提醒设为 true
                commodityVo.setInventoryWarning(true);
            }
            return commodityVo;
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean addCommodity(CommodityRequest request) {
        Commodity commodity = BeanCopyUtils.copyBean(request, Commodity.class);
        return save(commodity);
    }

    @Override
    public List<Comments> comments(CommentRequest request) {
        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品不存在");
        }
        Commodity commodity = getById(request.getId());
        if (commodity == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品不存在");
        }
        List<Comments> commentsList = JsonUtils.convertJsonList(commodity.getComments(), Comments.class);
        commentsList.add(request.getComments());
        commodity.setComments(JsonUtils.convertJsonString(commentsList));
        save(commodity);
        return commentsList;
    }
}




