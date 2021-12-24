package com.smilegate.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

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
    public ModelAndView verificationEmail(@PathVariable String email) {
        ModelAndView mav = new ModelAndView();

        String firstLine = email + "으로 가입 인증 메일을 전송했습니다.";
        String secondLine = "전송된 인증 링크는 5분간 유효합니다.";
        mav.addObject("firstLine", firstLine);
        mav.addObject("secondLine", secondLine);
        mav.setViewName("emailCompletePage");
        return mav;
    }

    @GetMapping("/confirmation")
    public String confirmation(@RequestParam String sign) {
        return "/confirmation";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/adminPage";
    }

//    @GetMapping("/confirmation")
//    public ModelAndView confirmation(@RequestParam String sign) {
//        ModelAndView mav = new ModelAndView();
//
//        mav.addObject("sign", sign);
//        mav.setViewName("confirmation");
//
//        return mav;
//    }
}