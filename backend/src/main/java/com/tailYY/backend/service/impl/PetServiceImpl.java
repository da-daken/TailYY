package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tailYY.backend.common.request.pet.PetEditRequest;
import com.tailYY.backend.common.request.pet.PetQueryRequest;
import com.tailYY.backend.common.util.BeanCopyUtils;
import com.tailYY.backend.common.util.FileUtils;
import com.tailYY.backend.common.util.JsonUtils;
import com.tailYY.backend.mapper.PetMapper;
import com.tailYY.backend.model.Class;
import com.tailYY.backend.model.json.BodyRecord;
import com.tailYY.backend.model.Pet;
import com.tailYY.backend.model.json.ServiceRecord;
import com.tailYY.backend.model.Vo.PetVo;
import com.tailYY.backend.service.ClassService;
import com.tailYY.backend.service.PetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 28447
* @description 针对表【pet(宠物)】的数据库操作Service实现
* @createDate 2025-01-11 16:25:01
*/
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

    @Resource
    private ClassService classService;

    @Override
    public Boolean editPetInfo(PetEditRequest petEditRequest) {
        // 编辑宠物信息
        // 将记录转为json
        Pet pet = BeanCopyUtils.copyBean(petEditRequest, Pet.class);
        pet.setBodyRecord(JsonUtils.convertJsonString(petEditRequest.getBodyRecord()));
        pet.setServiceRecord(JsonUtils.convertJsonString(petEditRequest.getServiceRecord()));
        return updateById(pet);
    }

    @Override
    public List<PetVo> getPetInfo(PetQueryRequest petQueryRequest) {
        Pet pet = BeanCopyUtils.copyBean(petQueryRequest, Pet.class);
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>(pet);
        List<Pet> pets = list(queryWrapper);
        return pets.stream().map(pet1 -> {
            PetVo petVo = BeanCopyUtils.copyBean(pet1, PetVo.class);
            petVo.setPic(FileUtils.convertFileToBase64(pet1.getPic()));
            petVo.setBodyRecord(JsonUtils.convertJsonList(pet1.getBodyRecord(), BodyRecord.class));
            petVo.setServiceRecord(JsonUtils.convertJsonList(pet1.getServiceRecord(), ServiceRecord.class));
            Class byId = classService.getById(pet1.getClassId());
            petVo.setClassId(pet1.getClassId());
            petVo.setClassName(byId.getClassName());
            return petVo;
        }).collect(Collectors.toList());
    }
}