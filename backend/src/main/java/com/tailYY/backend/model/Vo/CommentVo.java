package com.tailYY.backend.model.Vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/19
 **/
@Data
public class CommentVo implements Serializable {

    private String username;
    /**
     * 用户头像
     */
    private String avatar;
    private String comment;
    /**
     * 评论时间
     */
    private Date time = new Date();
    /**
     * 是否是购买后评价
     */
    private Boolean isBuyer;
}
