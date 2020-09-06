package com.challenge.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Component
@Slf4j
public class HttpMockMvcComponent {

    /**
     * @param uri END_POINT to send GET request. i.e "/items/MLU460998489"
     * @param id  Key of POJO with Long data type
     * @return RequestBuilder
     */
    public RequestBuilder getById(String uri, String id) {
        return MockMvcRequestBuilders
                .get(uri + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

}
