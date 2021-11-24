package com.example.microservices20;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoicingController {

    public static final String API_INVOICE = "/invoice";

    @PostMapping(API_INVOICE)
    public String invoice(@RequestBody String item){
        System.out.println("InvoicingController.invoice(): " + item);
        return "Invoiced: " + item;
    }
}
