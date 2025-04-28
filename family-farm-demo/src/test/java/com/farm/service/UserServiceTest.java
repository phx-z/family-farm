package com.farm.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @ClassName UserServiceTest
 * @Author phx
 * @Date 2025/4/25 13:59
 * @Description TODO
 */

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void getUsetListTest() {
        Assert.notEmpty(userService.getUserList(), "用户列表为空");
    }

}
