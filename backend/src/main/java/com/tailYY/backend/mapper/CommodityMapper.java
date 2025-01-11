package com.tailYY.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tailYY.backend.model.Commodity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 28447
* @description 针对表【commodity(宠物用品 & 宠物服务)】的数据库操作Mapper
* @createDate 2025-01-11 16:17:25
* @Entity generator.domain.Commodity
*/
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {

}




