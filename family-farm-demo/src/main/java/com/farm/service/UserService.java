package com.farm.service;


import com.farm.entity.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Author phx
 * @Date 2025/4/24 19:21
 * @Description TODO
 */


public interface UserService {

    User getUser();

    List<User> getUsetList();

}
