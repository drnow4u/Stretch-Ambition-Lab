package com.gitlab.marcinnowakcodes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
public class TestController {

    @Value("${external-service}")
    String serviceUrl;

    @GetMapping("/json")
    ResponseEntity<WelcomeResponse> getJson() {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        WelcomeResponse welcomeResponse = restTemplate.getForObject(serviceUrl + "/welcome?name=Teammate", WelcomeResponse.class, requestEntity);
        Objects.requireNonNull(welcomeResponse);

        if (!welcomeResponse.message.contains("Teammate"))
            return ResponseEntity.internalServerError()
                    .body(new WelcomeResponse("Response is not containing Teammate"));

        return ResponseEntity.ok(welcomeResponse);
    }

    @GetMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
    ResponseEntity<String> getHtml() {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(serviceUrl + "/testing", HttpMethod.GET, requestEntity, String.class);
    }

    public record WelcomeResponse(String message) {
    }
}
