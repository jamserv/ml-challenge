package com.challenge.health.controller;

import com.challenge.api.commons.Constants;
import com.challenge.health.model.Health;
import com.challenge.health.component.HealthHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
@RestController
@RequestMapping(Constants.URI_HEALTH)
@Lazy
@Slf4j
public class HealthController {

    @Autowired
    private HealthHandler handler;

    @GetMapping
    @ResponseBody
    Health listAll() {
        try {
            log.info("GET HEALTH FROM -> {} WITH ID={}", handler.getClass().getCanonicalName());
            return handler.getHealth();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return Health.builder().build();
        }
    }

}
