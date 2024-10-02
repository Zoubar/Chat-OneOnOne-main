package com.OneOnOne.Backend.service;

import com.OneOnOne.Backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User saveUser(User user);
    User findUserByEmail(String email);
    List<User> findAllUsers();



}
