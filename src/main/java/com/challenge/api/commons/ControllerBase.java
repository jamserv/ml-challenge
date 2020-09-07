package com.challenge.api.commons;

import com.challenge.health.component.HealthHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * @author janezmejias.09
 * @version V1
 * @param <T>
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
@Slf4j
public abstract class ControllerBase<T> {

    private final ServiceBase<T> service;

    @Autowired
    private HealthHandler healthServiceHandler;

    public ControllerBase(ServiceBase<T> service) {
        this.service = service;
    }

    @GetMapping(Constants.HTTP_GET_ONE)
    @ResponseBody
    Object getById(@PathVariable("id") String id) throws Exception {
        Long time = System.currentTimeMillis();
        try {
            log.info("GET ITEM FROM -> {} WITH ID={}", service.getClass().getCanonicalName(), id);            
            Optional<T> response = service.findById(id);
            healthServiceHandler.register(time, HttpStatus.OK.value());
            return response.get();
        } catch (ChallengeException e) {
            log.error(e.getLocalizedMessage(), e);            
            healthServiceHandler.register(time, e.getStatus_code());
            return ResponseBase.buildErrorResponse(e.getLocalizedMessage());
        }
    }

}
