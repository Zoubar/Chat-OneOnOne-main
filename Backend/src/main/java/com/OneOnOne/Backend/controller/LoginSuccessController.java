package com.OneOnOne.Backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginSuccessController {

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "loginSuccess"; // Return the name of the view
    }
}
