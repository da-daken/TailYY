package com.tailYY.backend.common.request.pet;

import com.tailYY.backend.model.json.BodyRecord;
import com.tailYY.backend.model.json.ServiceRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author daken 2025/1/12
 **/
@Data
public class PetEditRequest implements Serializable {
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
     * 宠物照片
     */
    private String pic;

    /**
     * 价格
     */
    private Double price;

    /**
     * 健康状态记录
     */
    private List<BodyRecord> bodyRecord;

    /**
     * 服务记录
     */
    private List<ServiceRecord> serviceRecord;

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
