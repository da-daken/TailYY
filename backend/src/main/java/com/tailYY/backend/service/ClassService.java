package com.tailYY.backend.service;

import com.tailYY.backend.model.Class;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
* @author 28447
* @description 针对表【class(类别)】的数据库操作Service
* @createDate 2025-01-11 16:14:23
*/
public interface ClassService extends IService<Class> {

    HashMap<Long, String> getAllClassName(Set<Integer> classIds);

    List<Class> getAllClass(Set<Integer> classIds);

    Boolean removeAndJudge(Long id);
}
