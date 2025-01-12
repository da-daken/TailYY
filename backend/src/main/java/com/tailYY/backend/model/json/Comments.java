package com.tailYY.backend.model.json;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/1/13
 **/
@Data
public class Comments implements Serializable {
    private String username;
    private String avatar;
    private String comment;
}
