package com.tailYY.backend.model.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tailYY.backend.model.json.Comments;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author daken 2025/1/13
 **/
@Data
public class CommodityVo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String commodity;

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
     * 类别id
     */
    private Long classId;

    /**
     * 商品类型 0 宠物用品 1 宠物服务
     */
    private String commodityType;

    /**
     * 商品种类id
     */
    private String className;

    /**
     * 库存 宠物服务没有库存
     */
    private Integer stockCount;

    /**
     * 最低库存提醒（宠物服务没有）
     */
    private Integer stockRemind;

    /**
     * 状态 0 正常， 1 关闭
     */
    private String status;

    /**
     * 评价列表
     */
    private List<CommentVo> comments;

    /**
     * 库存不足提醒
     */
    private Boolean inventoryWarning = false;

    /**
     * 过期时间（指这个商品的售后过期时间）
     */
    private Integer expireTime;

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
