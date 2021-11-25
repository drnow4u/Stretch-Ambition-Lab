package com.example.microservices20;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.microservices20.OrderController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OrderControllerTest {

    public static final String CLIENT_SECRET = "Secret Phrase";

    @Autowired
    MockMvc mvc;

    @Test
    void should_order() throws Exception {
        // Given
        final String item = "car";
        final String context = "" +
                "function onOrdered(item, invoicing) {" +
                "   var greeting='hello world';" +
                "   print(item);" +
                "   var invoiced = invoicing.invoice(item);" +
                "   return 'Thank you for ' + invoiced;" +
                "}" +
                "" +
                "function onInvoiced(item) {" +
                "   print(item);" +
                "   return item" +
                "}";
        // When
        final ResultActions response = mvc.perform(post(API_ORDER)
                .header(X_CONTEXT_HEADER, context)
                .header(X_DIGEST_HEADER, calculateDigest(context))
                .contentType(MediaType.APPLICATION_JSON)
                .content(item));
        // Then
        response.andExpect(status().isOk())
                .andExpect(content().string("Thank you for Invoiced: car"));
    }

    private String calculateDigest(String message) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(CLIENT_SECRET.getBytes());
        return Base64.encodeBase64String(messageDigest.digest(message.getBytes()));
    }

}
