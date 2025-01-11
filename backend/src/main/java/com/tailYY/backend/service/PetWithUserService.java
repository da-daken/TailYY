package com.tailYY.backend.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tailYY.backend.model.PetWithUser;

/**
* @author 28447
* @description 针对表【pet_with_user(用户和宠物关系)】的数据库操作Service
* @createDate 2025-01-11 16:27:19
*/
public interface PetWithUserService extends IService<PetWithUser> {

    int updatePetWithUser(PetWithUser petWithUser);

    PetWithUser getPetWithUser(PetWithUser petWithUser);
}
