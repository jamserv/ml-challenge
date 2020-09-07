package com.challenge.health.controller;

import com.challenge.api.commons.Constants;
import com.challenge.health.model.Health;
import com.challenge.health.component.HealthHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.URI_HEALTH)
@Lazy
@Log4j2
public class HealthController {

    @Autowired
    HealthHandler serviceHandler;

    @GetMapping
    @ResponseBody
    Health listAll() {
        try {            
            return serviceHandler.getHealth();            
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return Health.builder().build();
        }
    }

}
