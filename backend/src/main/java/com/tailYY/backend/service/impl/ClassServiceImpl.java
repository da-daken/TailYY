package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.exception.BusinessException;
import com.tailYY.backend.mapper.ClassMapper;
import com.tailYY.backend.mapper.CommodityMapper;
import com.tailYY.backend.model.Class;
import com.tailYY.backend.model.Commodity;
import com.tailYY.backend.model.Vo.CommodityVo;
import com.tailYY.backend.service.ClassService;
import com.tailYY.backend.service.CommodityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
* @author 28447
* @description 针对表【class(类别)】的数据库操作Service实现
* @createDate 2025-01-11 16:14:23
*/
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Resource
    private ClassMapper classMapper;


    @Resource
    private CommodityMapper mapper;

    @Override
    public HashMap<Long, String> getAllClassName(Set<Integer> classIds) {
        return classMapper.selectBatchIdsMap(classIds);
    }

    @Override
    public List<Class> getAllClass(Set<Integer> classIds) {
        return classMapper.selectBatchIdsClassMap(classIds);
    }

    @Override
    public Boolean removeAndJudge(Long id) {
        Commodity commodity = new Commodity();
        commodity.setClassId(Integer.valueOf(String.valueOf(id)));
        List<Commodity> list = mapper.getList(id);
        if (!list.isEmpty()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "有绑定宠物或者其他商品信息，请删除其他绑定的信息后在进行操作");
        }
        return removeById(id);
    }
}




