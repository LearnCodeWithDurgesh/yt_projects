package com.substring.sse.sseexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SSEController {


    @GetMapping("/stream")
    public Flux<String> stream(){

        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> "Hello " + i);
    }


}
