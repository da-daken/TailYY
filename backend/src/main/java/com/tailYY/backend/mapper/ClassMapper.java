package com.tailYY.backend.mapper;

import com.tailYY.backend.model.Class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author 28447
* @description 针对表【class(类别)】的数据库操作Mapper
* @createDate 2025-01-11 16:14:23
* @Entity generator.domain.Class
*/
@Mapper
public interface ClassMapper extends BaseMapper<Class> {

    @MapKey("id")
    HashMap<Long, String> selectBatchIdsMap(Set<Integer> classIds);

    @MapKey("id")
    List<Class> selectBatchIdsClassMap(Set<Integer> classIds);
}




