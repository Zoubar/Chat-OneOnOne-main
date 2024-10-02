package com.OneOnOne.Backend.controller;

import com.OneOnOne.Backend.entity.User;
import com.OneOnOne.Backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");

        // Console log to display name and email in server console
        System.out.println("User authenticated: " + username + " (" + email + ")");

        User user = userService.findUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUsername(username);
            userService.saveUser(user);
        }

        // Redirect to the Angular route
        response.sendRedirect("http://localhost:4200/loginSuccess");
    }
}
