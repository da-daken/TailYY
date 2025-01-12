package com.tailYY.backend.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author daken 2025/1/12
 **/
@Data
public class ServiceRecord implements Serializable {
    private String serviceName;
    private String assistantName;
    private Date serviceTime;
}
