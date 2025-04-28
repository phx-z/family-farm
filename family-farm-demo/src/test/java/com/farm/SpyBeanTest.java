package com.farm;


import com.farm.mock.MockBeanOption;
import com.farm.mock.MockBeanService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

/**
 * @ClassName SpyBeanAndMockBeanTest
 * @Author phx
 * @Date 2025/4/25 17:55
 * @Description 测试@SpyBean
 * 不模拟数据时，方法返回 真是方法的返回值，
 * 模拟数据时，返回模拟的数据
 * <p>
 *
 * <p>
 * Mockito.when(模拟对象.方法名(参数)).thenReturn(返回值)
 * when子句方法执行
 * <p>
 * Mockito.doReturn(返回值).when(模拟对象).方法名(参数)
 * <p>
 * thenReturn() 会触发真实方法，when子句执行
 * doReturn() 是 Mockito 中用于‌显式设置方法返回值‌的 API，其核心特性如下：
 * 跳过真实方法执行‌：直接覆盖方法行为，避免触发真实逻辑
 * 支持对 void 方法、静态方法（需结合 PowerMock）等特殊场景的模拟
 * @Spy对象： 部分模拟对象，默认执行真是方法，不设置模拟数据时，调用真实方法
 * thenReturn()会先执行真实方法（若存在）,，when子句执行，可能引发异常或副作用
 * doReturn()直接覆盖方法返回值，‌不触发真实方法‌,直接覆盖方法行为
 */

@Slf4j
@SpringBootTest
public class SpyBeanTest {

    @Autowired
    private MockBeanOption mockBeanOption;

    @SpyBean
    private MockBeanService mockBeanService;

    /**
     * 该测试方法用到了两个方法：mockBeanOption.getStr()，mockBeanService.getStr()
     * 模拟了MockBeanService对象中的getStr方法，
     * 所以mockBeanService.getStr()的返回值是：模拟mockBeanService中getStr方法的返回值
     * 最终打印数据为：========模拟mockBeanService中getStr方法的返回值 + MockBeanOption中getStr方法的真实返回值========
     * <p>
     * 1. Mockito.then().thenReturn()：@Spy对象会先执行真实方法（若存在），可能引发异常或副作用
     * Mockito.when(模拟对象.方法名(参数)).thenReturn(返回值)
     * 2. Mockito.doReturn().when()：@Spy对象直接覆盖方法返回值，‌不触发真实方法‌，适用于Spy或需绕过真实逻辑的场景
     * Mockito.doReturn(返回值).when(模拟对象).方法名(参数)
     */
    @Test
    void spyBeanTestA() {
        /**
         * Mockito.when，@Spy对象调用真实方法，
         * debug：
         *      -> Mockito.when
         *      -> mockBeanService中getStr方法
         *      -> String result = mockBeanOption.getStr();
         *      -> mockBeanOption中getStr方法
         *      -> log.info
         *
         */
//        Mockito.when(mockBeanService.getStr()).thenReturn(new String("模拟mockBeanService中getStr方法的返回值"));
        /**
         * Mockito.doReturn，@Spy对象绕过真实方法执行，
         * debug：
         *      -> Mockito.doReturn
         *      -> mockBeanService中getStr方法
         *      -> String result = mockBeanOption.getStr();
         *      -> mockBeanOption中getStr方法
         *      -> log.info
         */
        Mockito.doReturn(new String("模拟mockBeanService中getStr方法的返回值")).when(mockBeanService).getStr();
        String result = mockBeanOption.getStr();
        log.info("========" + result + "========");
        // 验证 getStr 调用一次
        Mockito.verify(mockBeanService, Mockito.times(1)).getStr();

    }

    /**
     * 该测试方法用到了两个方法：mockBeanOption.getStr()，mockBeanService.getStr()
     * 没有模拟MockBeanService对象中的getStr方法，
     * 所以mockBeanService.getStr()的返回值是：MockBeanService中getStr方法的真实返回值，
     * 最终打印数据为：========MockBeanService中getStr方法的真实返回值 + MockBeanOption中getStr方法的真实返回值========
     */
    @Test
    void spyBeanTest() {
        log.info("========" + mockBeanOption.getStr() + "========");
    }


}
