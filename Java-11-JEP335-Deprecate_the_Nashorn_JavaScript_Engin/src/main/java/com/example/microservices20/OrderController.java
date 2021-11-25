package com.example.microservices20;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import static com.example.microservices20.InvoicingController.API_INVOICE;

@RestController
public class OrderController {

    public static final String API_ORDER = "/order";
    public static final String X_CONTEXT_HEADER = "X-CONTEXT";

    private final int serverPort;
    private final ScriptEngine engine;

    public OrderController(@Value("${server.port}") int serverPort, ScriptEngine engine) {
        this.serverPort = serverPort;
        this.engine = engine;
    }

    @PostMapping(API_ORDER)
    public String order(@RequestBody String order, @RequestHeader(X_CONTEXT_HEADER) String context) throws ScriptException, NoSuchMethodException {
        Object result = engine.eval(context);
        System.out.println(result);

        Invocable invocable = (Invocable) engine;

        Object funcResult = invocable.invokeFunction("onOrdered", order, new InvoicingProxy(new RestTemplateBuilder()
                .defaultHeader(X_CONTEXT_HEADER, context)
                .rootUri("http://localhost:" + serverPort)
                .build()));
        System.out.println(funcResult);
        return funcResult.toString();
    }

    public static class InvoicingProxy {
        private final RestTemplate restTemplate;

        public InvoicingProxy(RestTemplate restTemplate) {

            this.restTemplate = restTemplate;
        }

        public String invoice(String item) {
            System.out.println("Invoicing: " + item);
            ResponseEntity<String> response = restTemplate.postForEntity(API_INVOICE, item, String.class);
            return response.getBody();
        }
    }
}
