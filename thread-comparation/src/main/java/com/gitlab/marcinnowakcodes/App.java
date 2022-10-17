package com.gitlab.marcinnowakcodes;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.*;

public class App {

    public static final int WARMUP_ITERATION = 1000_000;
    public static final int MEASURE_ITERATION = 1000_000;
    public static final Base64.Encoder ENCODER = Base64.getEncoder();

    public static void main(String[] args) throws Exception {
        experiment();
        experimentThread();
    }

    private static void experimentThread() throws ExecutionException, InterruptedException {
        // Warm up
        for (int i = 0; i < WARMUP_ITERATION; i++) {
            threadCalculation();
        }
        // Measure
        long startTimer = System.nanoTime();
        for (int i = 0; i < MEASURE_ITERATION; i++) {
            threadCalculation();
        }
        System.out.printf("With thread: %d us \n", TimeUnit.NANOSECONDS.toMicros((System.nanoTime() - startTimer) / MEASURE_ITERATION));
    }

    private static void experiment() throws Exception {
        // Warm up
        for (int i = 0; i < WARMUP_ITERATION; i++) {
            calculation();
        }
        // Measure
        long startTimer = System.nanoTime();
        for (int i = 0; i < MEASURE_ITERATION; i++) {
            calculation();
        }
        System.out.printf("Without thread: %d us \n", TimeUnit.NANOSECONDS.toMicros((System.nanoTime() - startTimer) / MEASURE_ITERATION));
    }

    static ExecutorService executor = Executors.newFixedThreadPool(100);
    volatile static String blackhole;

    public static void calculation() throws Exception {
        Callable<String> heavyCalculation = () -> {
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[10];
            random.nextBytes(bytes);
            return ENCODER.encodeToString(bytes);
        };
        var response = heavyCalculation.call();
        blackhole = response;
    }

    public static void threadCalculation() throws ExecutionException, InterruptedException {
        Callable<String> heavyCalculation = () -> {
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[10];
            random.nextBytes(bytes);
            return ENCODER.encodeToString(bytes);
        };
        var future = executor.submit(heavyCalculation);

        var response = future.get();
        blackhole = response;
    }

}
