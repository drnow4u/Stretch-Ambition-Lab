package com.gitlab.marcinnowakcodes;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0, stubs="classpath:/contracts")
@AutoConfigureMockMvc
class WiremockDemoApplicationTests {

    @Autowired
    MockMvc mvc;


    @Test
    void shouldTestPositivePathWithStubs() throws Exception {
        // Given

        // When
        var response = mvc.perform(get("/json"));

        // Then
        response.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.message").value("Hello Teammate"));
        verify(getRequestedFor(urlEqualTo("/welcome?name=Teammate")));
    }

    @Test
    void shouldTestAlternativeWithProgrammaticStubs() throws Exception {
        // Given
        stubFor(WireMock.get(urlMatching("/welcome\\?name=.*")).willReturn(aResponse()  // It will override response of existing JSON stub in resources/contracts/welcome.json
                .withHeader("Content-Type", "application/json")                     // Check section Stub priority in https://wiremock.org/docs/stubbing/
                .withBody("""
                        {
                            "message": "Hello Team"
                        }
                        """)
                .withTransformers("response-template")));

        // When
        var response = mvc.perform(get("/json"));

        // Then
        response.andDo(print())
                .andExpectAll(
                        status().is5xxServerError(),
                        jsonPath("$.message").value("Response is not containing Teammate"));
        verify(getRequestedFor(urlEqualTo("/welcome?name=Teammate")));
    }

    @Test
    void shouldTestHTMLWithProgrammaticStubs() throws Exception {
        // Given
        stubFor(WireMock.get(urlEqualTo("/testing")).willReturn(aResponse()
                .withHeader("Content-Type", "text/html")
                .withBodyFile("example.html")));

        // When
        var response = mvc.perform(get("/html"));

        // Then
        response.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        xpath("/html/body/H1").string("Hello World!"));
        verify(getRequestedFor(urlEqualTo("/testing")));
    }

}
