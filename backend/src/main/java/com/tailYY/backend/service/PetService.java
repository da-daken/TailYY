package com.tailYY.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tailYY.backend.common.request.PetEditRequest;
import com.tailYY.backend.common.request.PetQueryRequest;
import com.tailYY.backend.model.Pet;
import com.tailYY.backend.model.Vo.PetVo;

import java.util.List;

/**
* @author 28447
* @description 针对表【pet(宠物)】的数据库操作Service
* @createDate 2025-01-11 16:25:01
*/
public interface PetService extends IService<Pet> {

    Boolean editPetInfo(PetEditRequest petEditRequest);

    List<PetVo> getPetInfo(PetQueryRequest petQueryRequest);
}
