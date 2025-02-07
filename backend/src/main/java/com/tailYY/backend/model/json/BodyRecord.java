package com.tailYY.backend.model.json;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/12
 **/
@Data
public class BodyRecord implements Serializable {
    private Long bodyId;
    private String bodyStatus;
    private Date time;
    private String remark;
}
