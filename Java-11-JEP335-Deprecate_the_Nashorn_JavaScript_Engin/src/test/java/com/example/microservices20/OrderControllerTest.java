package com.example.microservices20;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.microservices20.OrderController.API_ORDER;
import static com.example.microservices20.OrderController.X_CONTEXT_HEADER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void should_order() throws Exception {
        // Given
        final String item = "car";
        final String context = "function onOrdered(item, invoicing) {" +
                "var greeting='hello world';" +
                "print(greeting);" +
                "print(item);" +
                "var invoiced = invoicing.invoice(item);" +
                "return 'Thank you for ' + invoiced;" +
                "}";
        // When
        final ResultActions response = mvc.perform(post(API_ORDER)
                .header(X_CONTEXT_HEADER, context)
                .contentType(MediaType.APPLICATION_JSON)
                .content(item));
        // Then
        response.andExpect(status().isOk())
                .andExpect(content().string("Thank you for Invoiced: car"));
    }

}
