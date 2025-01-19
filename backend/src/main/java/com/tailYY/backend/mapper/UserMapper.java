package com.tailYY.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tailYY.backend.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Set;

/**
* @author 28447
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2025-01-11 16:29:08
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    HashMap<Integer, String> selectBatchIdsMap(Set<Integer> userIds);
}




