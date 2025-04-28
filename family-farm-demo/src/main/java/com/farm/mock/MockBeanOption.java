package com.farm.mock;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MockBeanOption
 * @Author phx
 * @Date 2025/4/25 18:41
 * @Description TODO
 */

@Component
public class MockBeanOption {

    @Autowired
    private MockBeanService mockBeanService;

    public String getStr() {
        return mockBeanService.getStr() + " + MockBeanOption中getStr方法的真实返回值";
    }
}
