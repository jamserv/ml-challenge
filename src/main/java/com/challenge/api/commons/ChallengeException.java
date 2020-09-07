package com.challenge.api.commons;

import lombok.Builder;
import lombok.Data;

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
@Data
@Builder
public class ChallengeException extends Exception {

    private Integer status_code;
    private String message;        

}
