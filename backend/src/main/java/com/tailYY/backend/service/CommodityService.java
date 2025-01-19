package com.tailYY.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tailYY.backend.common.request.commodity.CommentRequest;
import com.tailYY.backend.common.request.commodity.CommodityRequest;
import com.tailYY.backend.model.Commodity;
import com.tailYY.backend.model.Vo.CommodityVo;
import com.tailYY.backend.model.json.Comments;

import java.util.List;

/**
* @author 28447
* @description 针对表【commodity(宠物用品 & 宠物服务)】的数据库操作Service
* @createDate 2025-01-11 16:17:25
*/
public interface CommodityService extends IService<Commodity> {

    List<CommodityVo> getAndNotify(Commodity commodity);

    Boolean addCommodity(CommodityRequest request);

    List<Comments> comments(CommentRequest request);

    void addOrderComments(Integer goodsId, Comments comments);

    Commodity getByIdLock(Integer goodsId);
}
