package com.tailYY.backend.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/12
 **/
@Data
public class BodyRecord implements Serializable {
    private String bodyStatus;
    private Date time;
    private String remark;
}
