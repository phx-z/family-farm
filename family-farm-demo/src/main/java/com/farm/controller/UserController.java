package com.farm.controller;


import com.farm.entity.User;
import com.farm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName UserController
 * @Author phx
 * @Date 2025/4/24 19:21
 * @Description TODO
 */

@RestController
@RequestMapping("/family/farm/demo")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public User getUser() {
        return userService.getUser();
    }

    @GetMapping("/list")
    public List<User> getUsetList() {
        return userService.getUsetList();
    }

}
