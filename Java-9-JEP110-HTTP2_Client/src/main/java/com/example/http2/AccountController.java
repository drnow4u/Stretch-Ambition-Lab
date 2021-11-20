package com.example.http2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;

@RestController
public class AccountController {

    @GetMapping(path = "/account", produces = {"text/plain"})
    public String getAccounts(HttpServletRequest request, PushBuilder pushBuilder) {
        if (null != pushBuilder) {
            pushBuilder.path("/account/1234/transaction")
                    .push();
        }
        return """
                [{"accountId": "1234"}]
               """;
    }

    @GetMapping(path = "/account/{accountId}/transaction", produces = {"text/plain"})
    public String getTransactions(@PathVariable(name = "accountId") String accountId) {
        return """
                [
                    {
                        "date": "2021.03.03T10:11:12",
                        "currency": "EUR",
                        "amount": 10.00
                    },
                    {
                        "date": "2021.03.04T11:12:13",
                        "currency": "EUR",
                        "amount": 13.56
                    },
                ]
                """;
    }

}
