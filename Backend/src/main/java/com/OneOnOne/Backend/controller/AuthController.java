package com.OneOnOne.Backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Adjust this as needed
public class AuthController {

    @PostMapping("/api/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.logout();
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
