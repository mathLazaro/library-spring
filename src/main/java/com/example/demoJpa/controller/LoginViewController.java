package com.example.demoJpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/authorized")
    @ResponseBody
    public String authorizedPage() {
        return "Authorized";
    }
}
