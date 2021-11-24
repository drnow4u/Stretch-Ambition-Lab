package com.example.microservices20;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.script.*;

import static com.example.microservices20.InvoicingController.API_INVOICE;

@RestController
public class OrderController {

    public static final String API_ORDER = "/order";
    public static final String X_CONTEXT_HEADER = "X-CONTEXT";

    @Value("${server.port}")
    private int serverPort;

    @PostMapping(API_ORDER)
    public String order(@RequestBody String order, @RequestHeader(X_CONTEXT_HEADER) String context) throws ScriptException, NoSuchMethodException {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine(new NoJavaFilter());

        Object result = engine.eval(context);
        System.out.println(result);

        Invocable invocable = (Invocable) engine;

        Object funcResult = invocable.invokeFunction("onOrdered", order, new InvoicingProxy(new RestTemplateBuilder()
                .rootUri("http://localhost:" + serverPort)
                .build()));
        System.out.println(funcResult);
        return (String)funcResult;
    }

    public static class InvoicingProxy {
        private final RestTemplate restTemplate;

        public InvoicingProxy(RestTemplate restTemplate) {

            this.restTemplate = restTemplate;
        }

        public String invoice(String item){
            System.out.println("Invoicing: " + item);
            ResponseEntity<String> response = restTemplate.postForEntity( API_INVOICE, item, String.class);
            return response.getBody();
        }
    }

    private class NoJavaFilter implements ClassFilter {
        @Override
        public boolean exposeToScripts(String className) {
            return false;
        }
    }
}
