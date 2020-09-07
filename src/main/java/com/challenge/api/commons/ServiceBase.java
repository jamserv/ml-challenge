package com.challenge.api.commons;

import java.util.Optional;

/**
 * @author janezmejias.09
 * @version V1
 * @param <T>
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
public interface ServiceBase<T> {

    Optional<T> findById(String id) throws ChallengeException;

    T save(T model) throws Exception;

}
