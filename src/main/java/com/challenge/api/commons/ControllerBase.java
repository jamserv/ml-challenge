package com.challenge.api.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
@Slf4j
public abstract class ControllerBase<T> {

    private final ServiceBase<T> service;

    public ControllerBase(ServiceBase<T> service) {
        this.service = service;
    }

    @GetMapping(Constants.HTTP_GET_ALL)
    @ResponseBody
    ResponseBase listAll() {
        try {
            log.info("GET ALL FROM -> {}", service.getClass().getCanonicalName());
            List<T> response = service.findAll();
            return ResponseBase.buildSuccessResponse(response);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseBase.buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @GetMapping(Constants.HTTP_GET_ONE)
    @ResponseBody
    Object getById(@PathVariable("id") String id) {
        try {
            log.info("GET ONE FROM -> {} WITH ID={}", service.getClass().getCanonicalName(), id);
            Optional<T> response = service.findById(id);
            return response.get();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseBase.buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @GetMapping(Constants.HTTP_GET_CHILDREN)
    @ResponseBody
    Optional<T> getChildrenById(@PathVariable("id") String id) {
        try {
            log.info("GET CHILDREN FROM -> {} WITH ID={}", service.getClass().getCanonicalName(), id);
            return service.findById(id);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return Optional.empty();
        }
    }

    @PostMapping(Constants.HTTP_POST)
    @ResponseBody
    ResponseBase create(@RequestBody T model) {
        try {
            log.info("POST {}", model.toString());
            T response = service.save(model);
            return ResponseBase.buildSuccessResponse(response);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseBase.buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @PutMapping(Constants.HTTP_PUT)
    @ResponseBody
    ResponseBase update(@RequestBody T model) {
        try {
            log.info("PUT {}", model.toString());
            T response = service.save(model);
            return ResponseBase.buildSuccessResponse(response);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseBase.buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @DeleteMapping(Constants.HTTP_DELETE)
    @ResponseBody
    ResponseBase delete(@RequestParam("id") String id) {
        try {
            log.info("DELETE ONE OF -> {} WITH ID={}", service.getClass().getCanonicalName(), id);
            service.deleteById(id);
            return ResponseBase.buildSuccessWithOutBodyResponse();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseBase.buildErrorResponse(e.getLocalizedMessage());
        }
    }

}
