package com.example.microservices20;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import static com.example.microservices20.OrderController.X_CONTEXT_HEADER;

@RestController
public class InvoicingController {

    public static final String API_INVOICE = "/invoice";

    private final ScriptEngine engine;

    public InvoicingController(ScriptEngine engine) {
        this.engine = engine;
    }

    @PostMapping(API_INVOICE)
    public String invoice(@RequestBody String item, @RequestHeader(X_CONTEXT_HEADER) String context) throws ScriptException, NoSuchMethodException {
        System.out.println("InvoicingController.invoice(): " + item);
        Object result = engine.eval(context);
        System.out.println(result);

        Invocable invocable = (Invocable) engine;

        Object funcResult = invocable.invokeFunction("onInvoiced", item);

        return "Invoiced: " + funcResult.toString();
    }
}
