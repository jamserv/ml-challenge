package com.challenge.api.commons;

import com.challenge.health.component.HealthHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

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
        try {
            Long time = System.currentTimeMillis();
            log.info("GET ITEM FROM -> {} WITH ID={}", service.getClass().getCanonicalName(), id);
            Optional<T> response = service.findById(id);
            healthServiceHandler.register(time);
            return response.get();
        } catch (ChallengeException e) {            
            log.error(e.getLocalizedMessage(), e);
            healthServiceHandler.registerError(e.getStatus_code());
            return ResponseBase.buildErrorResponse(e.getLocalizedMessage());
        }
    }

}
