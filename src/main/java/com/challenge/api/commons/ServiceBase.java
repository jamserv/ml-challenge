package com.challenge.api.commons;

import java.util.List;
import java.util.Optional;

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
public interface ServiceBase<T> {

    List<T> findAll() throws Exception;

    Optional<T> findById(String id) throws Exception;

    T save(T model) throws Exception;

    Boolean existsById(String id) throws Exception;

    Long count() throws Exception;

    void deleteById(String id) throws Exception;

}
