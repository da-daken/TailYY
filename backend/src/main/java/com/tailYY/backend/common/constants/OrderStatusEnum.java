package com.tailYY.backend.common.constants;


public enum OrderStatusEnum {

    WAIT_PAY("0", "待支付"),
    WAIT_SEND("1", "待发货"),
    WAIT_SIGN("2", "待签收"),
    WAIT_COMMENT("3", "待评价"),
    SUCCESS("4", "已完成"),
    WAIT_BACK("5", "待退货"),
    CANCEL("6", "已取消"),
    APPLYING("7", "退换货申请中")
    ;
    OrderStatusEnum(String code, String desc) {
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
