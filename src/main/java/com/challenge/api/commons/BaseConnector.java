package com.challenge.api.commons;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

/**
 * @author janezmejias.09
 * @version V1
 * @see <https://api.mercadolibre.com/items/MLU460998489>
 */
public abstract class BaseConnector {

    protected <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) throws RestClientException {
        HttpHeaders headers = getHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        return getRestOperations().exchange(url, HttpMethod.GET, entity, responseType);
    }

    protected <T> ResponseEntity<T> post(String url, Object body, ParameterizedTypeReference<T> responseType) throws RestClientException {
        HttpHeaders headers = getHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return getRestOperations().exchange(url, HttpMethod.POST, entity, responseType);
    }

    protected <T> ResponseEntity<T> put(String url, Object body, ParameterizedTypeReference<T> responseType) throws RestClientException {
        HttpHeaders headers = getHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return getRestOperations().exchange(url, HttpMethod.PUT, entity, responseType);
    }

    protected <T> ResponseEntity<T> delete(String url, Object body, ParameterizedTypeReference<T> responseType) throws RestClientException {
        HttpHeaders headers = getHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return getRestOperations().exchange(url, HttpMethod.DELETE, entity, responseType);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    protected abstract RestOperations getRestOperations();
}
