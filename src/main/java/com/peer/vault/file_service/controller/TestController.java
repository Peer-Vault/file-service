package com.peer.vault.file_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello Manish";
    }
}

