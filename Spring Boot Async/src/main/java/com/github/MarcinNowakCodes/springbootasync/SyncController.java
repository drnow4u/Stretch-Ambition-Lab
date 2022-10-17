package com.github.MarcinNowakCodes.springbootasync;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class SyncController {

    private final AsyncParentService asyncParentService;

    public SyncController(AsyncParentService asyncParentService) {
        this.asyncParentService = asyncParentService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<String> calculate() throws ExecutionException, InterruptedException {
        var result = asyncParentService.calculate();
        String s = result.get();
        return ResponseEntity.ok(s);
    }
}
