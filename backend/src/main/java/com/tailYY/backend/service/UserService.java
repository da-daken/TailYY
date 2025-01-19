package com.tailYY.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tailYY.backend.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Set;

/**
* @author 28447
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-01-11 16:29:08
*/
public interface UserService extends IService<User> {

    long userRegister(String username, String password, String checkPassword);

    User userLogin(String username, String password, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    boolean userLogout(HttpServletRequest request);

    HashMap<Integer, String> getAllUserName(Set<Integer> userIds);
}
