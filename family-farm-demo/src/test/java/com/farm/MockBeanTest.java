package com.farm;


import com.farm.entity.User;
import com.farm.mock.MockBeanOption;
import com.farm.mock.MockBeanService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

/**
 * @ClassName SpyBeanAndMockBeanTest
 * @Author phx
 * @Date 2025/4/25 17:55
 * @Description 测试@MockBean
 * <p>
 * <p>
 * ‌默认行为风险‌：@MockBean 不模拟数据时，方法返回 null 或默认值，可能导致空指针异常或断言失败。
 * ‌最佳实践‌：始终通过 Mockito.when() 显式定义关键方法的行为，确保测试可控性。
 * ‌适用场景‌：若仅需验证方法是否被调用（不关心返回值），可不模拟数据，直接使用 Mockito.verify()。
 * <p>
 * <p>
 * <p>
 * thenReturn() 会触发真实方法，when子句执行
 * doReturn() 是 Mockito 中用于‌显式设置方法返回值‌的 API，其核心特性如下：
 * 跳过真实方法执行‌：直接覆盖方法行为，避免触发真实逻辑
 * 支持对 void 方法、静态方法（需结合 PowerMock）等特殊场景的模拟
 * @Mock对象：全部模拟对象，默认不执行真实方法，不设置模拟数据时，按照默认行为执行 默认行为‌：所有方法返回值均为“空值”（如 null、0、false 等），‌不执行真实方法逻辑‌。
 * 方法存根‌：需显式配置返回值（否则返回默认值）。
 * doReturn()与thenReturn()等效‌，区别在于语法风格和适用场景，
 * thenReturn() 是首选语法，代码更简洁
 */

@Slf4j
@SpringBootTest
public class MockBeanTest {

    @Autowired
    private MockBeanOption mockBeanOption;

    @MockBean
    private MockBeanService mockBeanService;


    /**
     * Mockito.verify(mockList, Mockito.times(1))与Mockito.verify(mockList)‌的等价性‌
     * 两种写法 ‌功能完全等价‌，均用于验证 指定方法被调用一次
     * <p>
     * Argument(s) are different! 参数匹配失败
     * mockList.add()中add参数与Mockito.verify().add()中add参数不一样;
     * Wanted:Wanted but not invoked:方法未被调用
     * 没有执行：mockList.add();
     */
    @Test
    public void mockTest() {
        List<User> mockList = Mockito.mock(List.class);
        mockList.add(new User(1, "ce"));
        // 验证mockList.add(new User(1, "ce"))方法被调用1次
        Mockito.verify(mockList, Mockito.times(1)).add(new User(1, "ce"));
    }

    /**
     * 该测试方法用到了两个方法：mockBeanOption.getStr()，mockBeanService.getStr()
     * 模拟了MockBeanService对象中的getStr方法，
     * 所以mockBeanService.getStr()的返回值是：模拟mockBeanService中getStr方法的返回值
     * 最终打印数据为：========模拟MockBeanService中getStr方法的返回值 + MockBeanOption中getStr方法的真实返回值========
     */
    @Test
    public void mockBeanTestA() {
//        Mockito.when(mockBeanService.getStr()).thenReturn(new String("模拟MockBeanService中getStr方法的返回值"));
        Mockito.doReturn(new String("模拟mockBeanService中getStr方法的返回值")).when(mockBeanService).getStr();
        log.info("========" + mockBeanOption.getStr() + "========");
    }

    /**
     * 该测试方法用到了两个方法：mockBeanOption.getStr()，mockBeanService.getStr()
     * 没有模拟MockBeanService对象中的getStr方法，
     * 所以mockBeanService.getStr()的返回值是null
     * 最终打印数据为：========null + MockBeanOption中getStr方法的真实返回值========
     */
    @Test
    public void mockBeanTest() {
        log.info("========" + mockBeanOption.getStr() + "========");
    }


}
