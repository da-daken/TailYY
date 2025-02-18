package com.tailYY.backend.model.Vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/2/18
 **/
@Data
public class UserVo implements Serializable {
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String useraccount;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户角色：0 店长 1店员 2 顾客
     */
    private String userrole;

}
