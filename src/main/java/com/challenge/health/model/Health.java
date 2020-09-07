package com.challenge.health.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Builder;

@Data
@Builder
public class Health implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Timestamp date;
    private Long avg_response_time;
    private Long total_request;
    private Long avg_response_time_api_calls;
    private Long total_count_api_calls;
}
