package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tailYY.backend.mapper.ClassMapper;
import com.tailYY.backend.model.Class;
import com.tailYY.backend.service.ClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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

    @Override
    public HashMap<Integer, String> getAllClass(Set<Integer> classIds) {
        return classMapper.selectBatchIdsMap(classIds);
    }
}




