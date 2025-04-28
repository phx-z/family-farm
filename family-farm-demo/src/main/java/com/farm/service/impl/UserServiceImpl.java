package com.farm.service.impl;


import com.farm.entity.User;
import com.farm.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Author phx
 * @Date 2025/4/24 19:21
 * @Description TODO
 */

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser() {
        return new User(1, "用户名");
    }


    @Override
    public User getUserByIndex(int index) {
        return this.getUserList().get(index);
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "用户名1"));
        userList.add(new User(2, "用户名2"));
        return userList;
    }
}
