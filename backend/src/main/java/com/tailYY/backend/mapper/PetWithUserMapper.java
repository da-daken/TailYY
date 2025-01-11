package com.tailYY.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tailYY.backend.model.PetWithUser;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 28447
* @description 针对表【pet_with_user(用户和宠物关系)】的数据库操作Mapper
* @createDate 2025-01-11 16:27:19
* @Entity generator.domain.PetWithUser
*/
@Mapper
public interface PetWithUserMapper extends BaseMapper<PetWithUser> {

}




