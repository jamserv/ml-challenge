package com.challenge.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Slf4j
public class PerformMockMvcComponent {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    HttpMockMvcComponent httpMockMvcComponent;

    public void performGetOne(String uri, String id) throws Exception {
        mockMvc.perform(httpMockMvcComponent.getById(uri, id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.item_id").value(id));
    }
}
