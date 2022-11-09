package com.github.MarcinNowakCodes.springbootasync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AsyncParentService {
    @Autowired
    AsyncChildService asyncChildService;

    @Async
    public CompletableFuture<String> calculate() {
        try {
            System.out.println(Thread.currentThread().getName() + ": Parent sleep");
            Thread.sleep(1000);
            var response = asyncChildService.calculate();
            return CompletableFuture.completedFuture(response.get());

        } catch (ExecutionException | InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ": Parent sleep problem");
            return CompletableFuture.completedFuture("Couldn't sleep");
        }
    }
}
