package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tailYY.backend.mapper.PetWithUserMapper;
import com.tailYY.backend.model.PetWithUser;
import com.tailYY.backend.service.PetWithUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 28447
* @description 针对表【pet_with_user(用户和宠物关系)】的数据库操作Service实现
* @createDate 2025-01-11 16:27:19
*/
@Service
public class PetWithUserServiceImpl extends ServiceImpl<PetWithUserMapper, PetWithUser> implements PetWithUserService {

    @Resource
    private PetWithUserMapper petWithUserMapper;


    @Override
    public int updatePetWithUser(PetWithUser petWithUser) {
        return petWithUserMapper.updateById(petWithUser);
    }

    @Override
    public PetWithUser getPetWithUser(PetWithUser petWithUser) {
        QueryWrapper<PetWithUser> queryWrapper = new QueryWrapper<PetWithUser>();
        return petWithUserMapper.selectOne(queryWrapper);
    }
}




