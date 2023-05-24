package com.github.dddvalueobject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.github.dddvalueobject.ValueObjectUtil.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldCreateClient() throws Exception {
        // Given
        // When
        final var response = mvc.perform(
                post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                	"demoId": "12344",
                                	"clientNumber": "1122334455",
                                	"accountNumber": "1122778899"
                                 }
                                """));
        // Then
        response.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.demoId").value(12344),
                        jsonPath("$.clientNumber").value(1122334455),
                        jsonPath("$.accountNumber").value(1122778899)
                );
    }

    @Test
    void shouldCollectionContains() {
        var accountNumbers = List.of(
                new AccountNumber("54f0hpo5n9"),
                new AccountNumber("54zw31q9gm"),
                new AccountNumber("54fa119szp"),
                new AccountNumber("54sq3811sk"),
                new AccountNumber("546lvop236")
        );

        assertFalse(accountNumbers.contains(new ClientNumber("0987654321"))); // Code will compile
                                                                              // Static code analyse will discover

        //assertFalse(is(new ClientNumber("0987654321")).in(accountNumbers));   // Will not compile
    }

}
