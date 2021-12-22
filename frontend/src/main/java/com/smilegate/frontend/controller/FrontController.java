package com.smilegate.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/verification/{email}")
    public ModelAndView verification(@PathVariable String email) {
        ModelAndView mav = new ModelAndView();

        String firstLine = email + "으로 가입 인증 메일을 전송했습니다.";
        String secondLine = "전송된 인증 링크는 5분간 유효합니다.";
        mav.addObject("firstLine", firstLine);
        mav.addObject("secondLine", secondLine);
        mav.setViewName("emailVerification");
        return mav;
    }
}