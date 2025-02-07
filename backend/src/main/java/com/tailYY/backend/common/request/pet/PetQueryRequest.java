package com.tailYY.backend.common.request.pet;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/12
 **/
@Data
public class PetQueryRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 宠物名称
     */
    private String petname;

    /**
     * 宠物种类
     */
    private Long classId;

    /**
     * 宠物信息
     */
    private String info;

    /**
     * 价格
     */
    private Double price;

    /**
     * 笼子号
     */
    private Integer zoneId;

    /**
     * 状态 0在售 1已售
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;
}
