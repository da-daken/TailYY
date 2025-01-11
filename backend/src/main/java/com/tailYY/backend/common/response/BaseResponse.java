package com.tailYY.backend.common.response;

import com.tailYY.backend.common.constants.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/11
 **/
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}

