package com.tailYY.backend.common.request.commodity;

import com.tailYY.backend.model.json.Comments;
import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/14
 **/
@Data
public class CommentRequest implements Serializable {
    /**
     * 商品id
     */
    private Integer id;

    /**
     * 是否是购买后评论
     */
    private Boolean isBuyer;

    /**
     * 评论
     */
    private Comments comments;
}
