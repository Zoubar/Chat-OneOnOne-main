package com.OneOnOne.Backend.controller;

import com.OneOnOne.Backend.entity.User;
import com.OneOnOne.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/user-info")
    public Map<String, String> getUserInfo(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
            throw new IllegalStateException("User not authenticated");
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("email", email);

        return userInfo;
    }

    @GetMapping("/api/user")
    public ResponseEntity<User> getUser(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }



}
