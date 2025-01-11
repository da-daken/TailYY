package com.tailYY.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tailYY.backend.model.Pet;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 28447
* @description 针对表【pet(宠物)】的数据库操作Mapper
* @createDate 2025-01-11 16:25:01
* @Entity generator.domain.Pet
*/
@Mapper
public interface PetMapper extends BaseMapper<Pet> {

}




