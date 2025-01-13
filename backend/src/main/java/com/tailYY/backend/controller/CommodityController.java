package com.tailYY.backend.controller;

import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.request.commodity.CommentRequest;
import com.tailYY.backend.common.request.commodity.CommodityRequest;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.BeanCopyUtils;
import com.tailYY.backend.common.util.JsonUtils;
import com.tailYY.backend.common.util.ResultUtils;
import com.tailYY.backend.model.Commodity;
import com.tailYY.backend.model.Vo.CommodityVo;
import com.tailYY.backend.model.json.Comments;
import com.tailYY.backend.service.CommodityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author daken 2025/1/13
 **/
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Resource
    private CommodityService commodityService;

    /**
     * 查询宠物用品
     * 每次查询需要判断一下库存是否够，发个提醒
     */
    @GetMapping("/getCommodity")
    public BaseResponse<List<CommodityVo>> getCommodity(Commodity commodity) {
        if (commodity == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(commodityService.getAndNotify(commodity));
    }

    /**
     * 添加商品
     */
    @PostMapping("/addCommodity")
    public BaseResponse<Boolean> addCommodity(@RequestBody CommodityRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(commodityService.addCommodity(request));
    }

    /**
     * 评论商品
     */
    @PostMapping("/comment")
    public BaseResponse<List<Comments>> comment(@RequestBody CommentRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(commodityService.comments(request));
    }

    /**
     * 编辑宠物
     */
    @PostMapping("/editCommodity")
    public BaseResponse<Boolean> updateCommodity(@RequestBody CommodityRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(commodityService.updateById(BeanCopyUtils.copyBean(request, Commodity.class)));
    }

    @PostMapping("/test")
    public void test(@RequestBody String request) {
        List<Comments> commentsList = JsonUtils.convertJsonList(request, Comments.class);
        commentsList.add(new Comments());
    }

    /**
     * 删除
     */
    @PostMapping("/deleteCommodity")
    public BaseResponse<Boolean> deleteCommodity(@RequestBody Commodity request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(commodityService.removeById(request));
    }
}
