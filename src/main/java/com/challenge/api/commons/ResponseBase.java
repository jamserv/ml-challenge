package com.challenge.api.commons;

import lombok.Builder;
import lombok.Data;

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
@Builder
@Data
public class ResponseBase {

    private Boolean success;
    private String message;
    private Object body;

    public static ResponseBase buildSuccessResponse(Object body) {
        return ResponseBase.builder()
                .success(Boolean.TRUE)
                .message(Messages.OK.name())
                .body(body)
                .build();
    }

    public static ResponseBase buildSuccessWithOutBodyResponse() {
        return ResponseBase.builder()
                .success(Boolean.TRUE)
                .message(Messages.ERROR.name())
                .build();
    }

    public static ResponseBase buildErrorResponse(String errorMessage) {
        return ResponseBase.builder()
                .success(Boolean.FALSE)
                .message(errorMessage)
                .build();
    }

}
