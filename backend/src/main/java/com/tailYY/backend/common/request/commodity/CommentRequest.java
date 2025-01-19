package com.tailYY.backend.common.request.commodity;

import com.tailYY.backend.model.json.Comments;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/14
 **/
@Data
public class CommentRequest implements Serializable {
    /**
     * 商品id
     */
    private Integer id;

    private Long userId;
    private String comment;
    /**
     * 评论时间
     */
    private Date time = new Date();

}
