package com.br.infnet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Aplicação está funcionando! " + System.currentTimeMillis();
    }

    @GetMapping("/")
    public String home() {
        return "Bem-vindo ao Sistema Infnet!";
    }
}