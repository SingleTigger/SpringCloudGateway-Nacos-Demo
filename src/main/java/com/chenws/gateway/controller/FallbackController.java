package com.chenws.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by chenws on 2019/11/4.
 */
@RestController
@RequestMapping("")
@Slf4j
public class FallbackController {

    @GetMapping("/fallback")
    public Mono<String>  fallback(){
        log.info("fallback");
        return Mono.just("fallback");
    }
}
