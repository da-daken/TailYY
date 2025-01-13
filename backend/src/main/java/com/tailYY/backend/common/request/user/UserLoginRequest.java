package com.tailYY.backend.common.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/11
 **/
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 38888888231L;

    private String username;

    private String password;
}
