package com.tailYY.backend.common.constants;

public enum ApplyEnum {
    NO_APPLY("0", "无退换货"),
    YES_APPLY("1", "有退换货"),

    ;
    ApplyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    String code;

    String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
