package com.tailYY.backend.controller;

import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.request.pet.PetEditRequest;
import com.tailYY.backend.common.request.pet.PetQueryRequest;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.ResultUtils;
import com.tailYY.backend.model.Pet;
import com.tailYY.backend.model.Vo.PetVo;
import com.tailYY.backend.service.PetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author daken 2025/1/12
 **/
@RestController
@RequestMapping("/pet")
public class PetController {

    @Resource
    private PetService petService;

    /**
     * 查询宠物信息
     */
    @GetMapping("/getPetInfo")
    public BaseResponse<List<PetVo>> getPetInfo(PetQueryRequest petQueryRequest) {
        return ResultUtils.success(petService.getPetInfo(petQueryRequest));
    }

    /**
     * 编辑、更新宠物信息
     */
    @PostMapping("/updatePetInfo")
    public BaseResponse<Boolean> updatePetInfo(@RequestBody PetEditRequest petEditRequest) {
        if (petEditRequest.getId() == null ||  petEditRequest.getPrice() == null || petEditRequest.getZoneId() == null
                || StringUtils.isAnyBlank(petEditRequest.getPetname(), petEditRequest.getInfo(), petEditRequest.getPic(), petEditRequest.getClassId())) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        if (petEditRequest.getIsdelete() == 1) {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR, "请检查宠物信息是否被删除");
        }
        return ResultUtils.success(petService.editPetInfo(petEditRequest));
    }

    /**
     * 添加宠物信息
     */
    @PostMapping("/addPetInfo")
    public BaseResponse<Boolean> addPetInfo(@RequestBody Pet pet) {
        if (pet.getPrice() == null || pet.getZoneId() == null || pet.getClassId() == null
                || StringUtils.isAnyBlank(pet.getPetname(), pet.getInfo(), pet.getPic())) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(petService.save(pet));
    }

    /**
     * 删除宠物信息
     */
    @PostMapping("/deletePetInfo")
    public BaseResponse<Boolean> deletePetInfo(Integer petId) {
        return ResultUtils.success(petService.removeById(petId));
    }
}
