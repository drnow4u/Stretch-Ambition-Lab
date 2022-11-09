package com.github.MarcinNowakCodes.springbootasync;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class SpringBootAsyncApplicationTests {

    @Autowired
    MockMvc mvc;


    @Test
    void testMockMvc() throws Exception {
        // Given
        // When
        var response = mvc.perform(get("/calculate"));

        // Then
        response.andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    void testRestTemplate() {
        // Given

        // When
        var count = IntStream.range(0, 5)
                .mapToObj(i -> new Thread(() -> {
                            System.out.println(Thread.currentThread().getName());
                            var restTemplate = new RestTemplate();
                            var response = restTemplate.getForEntity("http://localhost:8080/calculate", String.class);
                        })
                )
                .map(thread -> {
                    thread.start();
                    return thread;
                })
                .map(thread -> {
                    try {
                        thread.join();
                        return true;
                    } catch (InterruptedException e) {
                        return false;
                    }
                })
                .toList();

        // Then
        assertEquals(List.of(true, true, true, true,true), count);
    }

    @Test
    void forJoinPool(){
        ForkJoinPool forkJoinPool = new ForkJoinPool(5);

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
            var restTemplate = new RestTemplate();
            var response = restTemplate.getForEntity("http://localhost:8080/calculate", String.class);
        };

        var task1 = forkJoinPool.submit(runnable);
        var task2 = forkJoinPool.submit(runnable);
        var task3 = forkJoinPool.submit(runnable);
        var task4 = forkJoinPool.submit(runnable);

        task1.join();
        task2.join();
        task3.join();
        task4.join();
    }

}
