package com.tailYY.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.ResultUtils;
import com.tailYY.backend.model.Commodity;
import com.tailYY.backend.model.Vo.CommodityVo;
import com.tailYY.backend.service.CommodityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 添加
     */

    /**
     * 编辑宠物
     */

    /**
     * 删除
     */
}
