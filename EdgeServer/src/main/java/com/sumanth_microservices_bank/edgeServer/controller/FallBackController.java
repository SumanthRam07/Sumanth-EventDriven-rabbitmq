package com.sumanth_microservices_bank.edgeServer.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {



    @GetMapping("/contactSupport")
    public Mono<String>  ContactSuppot()
    {

       return  Mono.just("An Error has occured , please contact the support team ") ;
    }
}
