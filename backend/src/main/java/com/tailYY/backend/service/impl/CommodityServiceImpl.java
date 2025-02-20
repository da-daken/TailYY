package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tailYY.backend.common.constants.CommodityConstants;
import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.exception.BusinessException;
import com.tailYY.backend.common.request.commodity.CommentRequest;
import com.tailYY.backend.common.request.commodity.CommodityRequest;
import com.tailYY.backend.common.util.BeanCopyUtils;
import com.tailYY.backend.common.util.FileUtils;
import com.tailYY.backend.common.util.JsonUtils;
import com.tailYY.backend.mapper.CommodityMapper;
import com.tailYY.backend.model.Class;
import com.tailYY.backend.model.Commodity;
import com.tailYY.backend.model.User;
import com.tailYY.backend.model.Vo.CommentVo;
import com.tailYY.backend.model.Vo.CommodityVo;
import com.tailYY.backend.model.json.Comments;
import com.tailYY.backend.service.ClassService;
import com.tailYY.backend.service.CommodityService;
import com.tailYY.backend.service.UserService;
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

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private UserService userService;

    @Override
    public List<CommodityVo> getAndNotify(Commodity commodity) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>(commodity);
        List<Commodity> commodityList = list(queryWrapper);
        if (commodityList.size() == 0) {
            return Collections.emptyList();
        }
        // 提取所有唯一类别ID
        Set<Integer> classIds = commodityList.stream()
                .map(Commodity::getClassId)
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
        return commodityList.stream().map(commodity1 -> {
            CommodityVo commodityVo = BeanCopyUtils.copyBean(commodity1, CommodityVo.class);
            commodityVo.setPic(FileUtils.convertFileToBase64(commodity1.getPic()));
            List<Comments> commentsList = JsonUtils.convertJsonList(commodity1.getComments(), Comments.class);
            if (commentsList !=null && !commentsList.isEmpty()) {
                List<CommentVo> commentVoList = commentsList.stream()
                        .map(comments -> {
                            CommentVo commentVo = BeanCopyUtils.copyBean(comments, CommentVo.class);
                            User user = userService.getById(comments.getUserId());
                            commentVo.setAvatar(user.getAvatar());
                            commentVo.setUsername(user.getUsername());
                            return commentVo;
                        }).collect(Collectors.toList());
                commodityVo.setComments(commentVoList);
            }
            // 将类别转换成名字
            commodityVo.setClassId(Long.valueOf(commodity1.getClassId()));
            commodityVo.setClassName(classMap.get(Long.valueOf(commodity1.getClassId())).get("className"));
            commodityVo.setCommodityType(classMap.get(Long.valueOf(commodity1.getClassId())).get("classType"));
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
        Class classServiceById = classService.getById(Long.valueOf(request.getClassId()));
        commodity.setCommodityType(classServiceById.getClassType());
        return save(commodity);
    }

    @Override
    public Boolean comments(CommentRequest request) {
        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品不存在");
        }
        Commodity commodity = getById(request.getId());
        if (commodity == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品不存在");
        }
        List<Comments> commentsList = JsonUtils.convertJsonList(commodity.getComments(), Comments.class);
        if (commentsList == null) {
            commentsList = new ArrayList<>();
        }
        Comments comments = new Comments();
        comments.setComment(request.getComment());
        comments.setUserId(request.getUserId());
        comments.setIsBuyer(false);
        commentsList.add(comments);
        commodity.setComments(JsonUtils.convertJsonString(commentsList));
        return updateById(commodity);
    }

    public void addOrderComments(Integer goodsId, Comments comments) {
        Commodity commodity = getById(goodsId);
        if (commodity == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品不存在");
        }
        List<Comments> commentsList = JsonUtils.convertJsonList(commodity.getComments(), Comments.class);
        commentsList.add(comments);
        commodity.setComments(JsonUtils.convertJsonString(commentsList));
        updateById(commodity);
    }

    @Override
    public Commodity getByIdLock(Integer goodsId) {
        return commodityMapper.getByIdLock(goodsId);
    }
}




