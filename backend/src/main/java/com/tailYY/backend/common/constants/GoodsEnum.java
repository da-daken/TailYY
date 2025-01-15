package com.tailYY.backend.common.constants;

public enum GoodsEnum {

    GOODS("0", "宠物用品"),
    SERVICE("1", "宠物服务"),
    PET("2", "宠物")
    ;
    GoodsEnum(String code, String desc) {
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
