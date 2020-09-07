package com.challenge.api.commons;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author janez
 */
@Data
@Builder
public class ChallengeException extends Exception {

    private Integer status_code;
    private String message;        

}
