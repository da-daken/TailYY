package com.tailYY.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.exception.BusinessException;
import com.tailYY.backend.common.request.user.UserLoginRequest;
import com.tailYY.backend.common.request.user.UserPasswordRequest;
import com.tailYY.backend.common.request.user.UserRegisterRequest;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.BeanCopyUtils;
import com.tailYY.backend.common.util.FileUtils;
import com.tailYY.backend.common.util.ResultUtils;
import com.tailYY.backend.model.User;
import com.tailYY.backend.model.Vo.UserVo;
import com.tailYY.backend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daken 2025/1/11
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(username, password, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(username, password, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 查询用户信息
     *
     * @param user
     * @return
     */
    @GetMapping("/getUser")
    public BaseResponse<List<UserVo>> getUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        List<User> userList = userService.list(queryWrapper);
        List<UserVo> userVoList = userList.stream().map(user1 -> {
            UserVo userVo = BeanCopyUtils.copyBean(user1, UserVo.class);
            userVo.setAvatar(FileUtils.convertFileToBase64(userVo.getAvatar()));
            return userVo;
        }).collect(Collectors.toList());
        return ResultUtils.success(userVoList);
    }

    /**
     * 删除用户
     *
     * @param userid
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody User userid, HttpServletRequest request) {
        if (userid == null || userid.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(userid.getId());
        return ResultUtils.success(b);
    }

    /**
     * 编辑用户信息
     *
     * @param userUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody User userUpdateRequest, HttpServletRequest request) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.updateById(userUpdateRequest);
        return ResultUtils.success(result);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    public BaseResponse<Boolean> updatePassword(@RequestBody UserPasswordRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
                                      Assert.notNull(request.getUserid(), "userid is null");
        Assert.notNull(request.getOldPassword(), "oldPassword is null");
        Assert.notNull(request.getNewPassword(), "newPassword is null");

        return ResultUtils.success(userService.updatePassword(request));
    }
}
