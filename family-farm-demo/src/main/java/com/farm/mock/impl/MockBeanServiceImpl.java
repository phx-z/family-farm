package com.farm.mock.impl;


import com.farm.mock.MockBeanService;
import org.springframework.stereotype.Service;

/**
 * @ClassName MockBeanServiceImpl
 * @Author phx
 * @Date 2025/4/26 19:37
 * @Description TODO
 */

@Service
public class MockBeanServiceImpl implements MockBeanService {
    public String getStr() {
        return "MockBeanService中getStr方法的真实返回值";
    }
}
