package com.example.microservices20;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import java.security.NoSuchAlgorithmException;

import static com.example.microservices20.OrderController.*;

@RestController
public class InvoicingController {

    public static final String API_INVOICE = "/invoice";

    private final ScriptEngine engine;

    public InvoicingController(ScriptEngine engine) {
        this.engine = engine;
    }

    @PostMapping(API_INVOICE)
    public String invoice(@RequestBody String item,
                          @RequestHeader(X_CONTEXT_HEADER) String context,
                          @RequestHeader(X_DIGEST_HEADER) String digest) throws ScriptException, NoSuchMethodException, NoSuchAlgorithmException {
        if (!calculateDigest(context).equals(digest))
            throw new RuntimeException("Wrong digest: " + digest);
        System.out.println("InvoicingController.invoice(): " + item);
        Object result = engine.eval(context);
        System.out.println(result);

        Invocable invocable = (Invocable) engine;

        Object funcResult = invocable.invokeFunction("onInvoiced", item);

        return "Invoiced: " + funcResult.toString();
    }
}
