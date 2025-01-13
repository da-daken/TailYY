package com.tailYY.backend.common.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/11
 **/
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String username;

    private String password;

    private String checkPassword;
}
