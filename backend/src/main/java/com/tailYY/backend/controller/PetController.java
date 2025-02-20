package com.tailYY.backend.controller;

import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.request.pet.PetEditRequest;
import com.tailYY.backend.common.request.pet.PetQueryRequest;
import com.tailYY.backend.common.request.pet.PetRecordRequest;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.ResultUtils;
import com.tailYY.backend.model.Pet;
import com.tailYY.backend.model.Vo.PetVo;
import com.tailYY.backend.model.json.BodyRecord;
import com.tailYY.backend.model.json.ServiceRecord;
import com.tailYY.backend.service.PetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
                || StringUtils.isAnyBlank(petEditRequest.getPetname(), petEditRequest.getInfo(), petEditRequest.getPic())) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(petService.editPetInfo(petEditRequest));
    }

    /**
     * 新增宠物健康/服务记录
     */
    @PostMapping("/addRecord")
    public BaseResponse<Boolean> record(@RequestBody PetRecordRequest request) {
        PetQueryRequest petQueryRequest = new PetQueryRequest();
        petQueryRequest.setId(request.getId());
        List<PetVo> petInfo = petService.getPetInfo(petQueryRequest);
        PetVo petVo = petInfo.get(0);
        if (request.getBodyRecord() != null) {
            List<BodyRecord> bodyRecord = petVo.getBodyRecord();
            if (bodyRecord == null && bodyRecord.isEmpty()) {
                bodyRecord = new ArrayList<>();
                request.getBodyRecord().setBodyId(1L);
            } else {
                request.getBodyRecord().setBodyId(bodyRecord.get(bodyRecord.size() - 1).getBodyId() + 1);
            }
            bodyRecord.add(request.getBodyRecord());
            petVo.setBodyRecord(bodyRecord);
        } else if (request.getServiceRecord() != null) {
            List<ServiceRecord> serviceRecord = petVo.getServiceRecord();
            if (serviceRecord == null && serviceRecord.isEmpty()) {
                serviceRecord = new ArrayList<>();
                request.getServiceRecord().setServiceId(1L);
            } else {
                request.getServiceRecord().setServiceId(serviceRecord.get(serviceRecord.size() - 1).getServiceId() + 1);
            }
            serviceRecord.add(request.getServiceRecord());
            petVo.setServiceRecord(serviceRecord);
        }
        PetEditRequest petEditRequest = new PetEditRequest();
        petEditRequest.setId(request.getId());
        petEditRequest.setBodyRecord(petVo.getBodyRecord());
        petEditRequest.setServiceRecord(petVo.getServiceRecord());
        return ResultUtils.success(petService.editPetInfo(petEditRequest));
    }

    /**
     * 编辑宠物健康/服务记录
     */
    @PostMapping("editRecord")
    public BaseResponse<Boolean> editRecord(@RequestBody PetRecordRequest request) {
        PetQueryRequest petQueryRequest = new PetQueryRequest();
        petQueryRequest.setId(request.getId());
        List<PetVo> petInfo = petService.getPetInfo(petQueryRequest);
        PetVo petVo = petInfo.get(0);
        if (request.getBodyRecord() != null) {
            for (int i = 0; i < petVo.getBodyRecord().size(); i++) {
                if (petVo.getBodyRecord().get(i).getBodyId() == request.getBodyRecord().getBodyId()) {
                    petVo.getBodyRecord().set(i, request.getBodyRecord());
                    break;
                }
            }
        } else if (request.getServiceRecord() != null) {
            for (int i = 0; i < petVo.getServiceRecord().size(); i++) {
                if (petVo.getServiceRecord().get(i).getServiceId() == request.getServiceRecord().getServiceId()) {
                    petVo.getServiceRecord().set(i, request.getServiceRecord());
                    break;
                }
            }
        }
        PetEditRequest petEditRequest = new PetEditRequest();
        petEditRequest.setId(request.getId());
        petEditRequest.setBodyRecord(petVo.getBodyRecord());
        petEditRequest.setServiceRecord(petVo.getServiceRecord());
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
        pet.setStatus(0);
        return ResultUtils.success(petService.save(pet));
    }

    /**
     * 删除宠物信息
     */
    @PostMapping("/deletePetInfo")
    public BaseResponse<Boolean> deletePetInfo(@RequestBody Pet petId) {
        return ResultUtils.success(petService.removeById(petId.getId()));
    }
}
