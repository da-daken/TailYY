package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tailYY.backend.mapper.PetMapper;
import com.tailYY.backend.model.Pet;
import com.tailYY.backend.service.PetService;
import org.springframework.stereotype.Service;

/**
* @author 28447
* @description 针对表【pet(宠物)】的数据库操作Service实现
* @createDate 2025-01-11 16:25:01
*/
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

}




