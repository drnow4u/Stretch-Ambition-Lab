package com.github.MarcinNowakCodes.springbootasync;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncChildService {
    @Async
    public CompletableFuture<String> calculate() {
        try {
            System.out.println(Thread.currentThread().getName() + ": Child sleep");
            Thread.sleep(1000);
            return CompletableFuture.completedFuture("Slept well");

        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ": Child sleep problem");
            return CompletableFuture.completedFuture("Couldn't sleep");
        }
    }
}
