package com.tailYY.backend.common.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/2/18
 **/
@Data
public class UserPasswordRequest implements Serializable {
    private Long userid;
    private String oldPassword;
    private String newPassword;
}
