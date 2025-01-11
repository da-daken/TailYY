package com.tailYY.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tailYY.backend.mapper.UserMapper;
import com.tailYY.backend.model.User;
import com.tailYY.backend.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author 28447
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-01-11 16:29:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




