package com.smilegate.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FrontController {
    @GetMapping
    public String loginForm() {
        return "/loginForm";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "/registerForm";
    }
}
