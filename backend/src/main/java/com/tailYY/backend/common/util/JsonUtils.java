package com.tailYY.backend.common.util;

import com.alibaba.fastjson2.JSON;
import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author daken 2025/1/12
 **/
@Slf4j
public class JsonUtils {

    public static <T> List<T> convertJsonList(String source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            return JSON.parseArray(source, clazz);
        } catch (Exception e) {
            log.error("json转换list失败，入参为{}, e:{}", source, e.getMessage());
            throw e;
        }
    }

    public static <T> T convertJson(String source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            return JSON.parseObject(source, clazz);
        } catch (Exception e) {
            log.error("json转换对象失败，入参为{}, e:{}", source, e.getMessage());
            throw e;
        }
    }

    public static String convertJsonString(Object source) {
        if (source == null) {
            return null;
        }
        try {
            return JSON.toJSONString(source);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "传的参数有问题");
        }

    }
}
