package com.tailYY.backend.model.json;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/13
 **/
@Data
public class Comments implements Serializable {
    private String username;
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
