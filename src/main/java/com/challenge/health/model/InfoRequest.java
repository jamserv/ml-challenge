package com.challenge.health.model;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author janez
 */
@Data
@Builder
public class InfoRequest {

    private Integer status_code;
    private Integer count;
}
