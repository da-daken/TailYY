package com.tailYY.backend.common.request.pet;

import com.tailYY.backend.model.json.BodyRecord;
import com.tailYY.backend.model.json.ServiceRecord;
import lombok.Data;

import java.io.Serializable;

/**
 * @author daken 2025/2/7
 **/
@Data
public class PetRecordRequest implements Serializable {
    private Long id;
    private BodyRecord bodyRecord;
    private ServiceRecord serviceRecord;
}
