package com.farm.controller;


import com.farm.entity.User;
import com.farm.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

/**
 * @ClassName UserControllerWebMvcTest
 * @Author phx
 * @Date 2025/4/25 16:27
 * @Description 快速验证 Controller 层的 HTTP 请求处理逻辑与响应格式,显式模拟 Service 层依赖
 *
 * <p>
 * MockBean对象与SpyBean对象、thenReturn与doReturn的选择如下：
 * <p>
 * MockBean对象与SpyBean对象选择：@MockBean对象：
 * 因只测试Controller层的HTTP请求处理逻辑与响应格式,不需要执行真实方法，所以使用@MockBean对象全部模拟userService，
 * thenReturn与doReturn选择：thenReturn：
 * 在使用@Mock对象时，thenReturn与doReturn等效，使用thenReturn代码更简洁，所以使用thenReturn
 * <p>
 * thenReturn与doReturn、Mock对象与Spy对象的相关内容如下：
 * <p>
 * thenReturn() 会触发真实方法，when子句执行
 * doReturn() 是 Mockito 中用于‌显式设置方法返回值‌的 API，其核心特性如下：
 * 跳过真实方法执行‌：直接覆盖方法行为，避免触发真实逻辑
 * 支持对 void 方法、静态方法（需结合 PowerMock）等特殊场景的模拟。
 * <p>
 * Mock对象：全部模拟对象，默认不执行真实方法，不设置模拟数据时，按照默认行为执行：
 * 默认行为‌：所有方法返回值均为“空值”（如 null、0、false 等），‌不执行真实方法逻辑‌。
 * 方法存根‌：需显式配置返回值（否则返回默认值）。
 * doReturn()与thenReturn()等效‌，区别在于语法风格和适用场景，
 * thenReturn() 是首选语法，代码更简洁
 * <p>
 * Spy对象： 部分模拟对象，默认执行真是方法，不设置模拟数据时，调用真实方法
 * thenReturn()会先执行真实方法（若存在），when子句执行，可能引发异常或副作用
 * doReturn()直接覆盖方法返回值，‌不触发真实方法‌,直接覆盖方法行为
 */

@WebMvcTest(UserController.class) //仅加载 UserController 及 MVC 组件
public class UserControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

//    @SpyBean UserService 异常，@SpyBean UserServiceImpl正常
    @MockBean  // 显式模拟 Service 层依赖
    private UserService userService;

    /**
     * 测试controller中getUser方法
     * 模拟userService.getUser方法的返回值，
     * Body = {"id":3,"name":"模拟Service的返回数据"}
     * <p>
     * <p>
     * 对于Mock对象，thenReturn与doReturn方法的等效，不会执行真实方法，when子句不会执行
     * debug时，任何断点都不走
     *
     * @throws Exception
     */
    @Test
    public void getUserTest() throws Exception {
        // 模拟 Service 返回有效数据
//        Mockito.when(userService.getUser()).thenReturn(new User(3, "模拟Service的返回数据"));
        Mockito.doReturn(new User(3, "模拟Service的返回数据")).when(userService).getUser();
        // 发送请求并打印响应
        mockMvc.perform(MockMvcRequestBuilders
                        //构造请求
                        .get("/family/farm/demo/user")
                        //设置返回值类型
                        .accept(MediaType.APPLICATION_JSON)
                )
                //添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("模拟Service的返回数据"))  // 验证中文字段值:ml-citation{ref="4,7" data="citationList"}
                //添加一个结果处理器，此处打印整个响应结果信息
                .andDo(MockMvcResultHandlers.print());


        // 验证真实方法是否被调用1次
        Mockito.verify(userService).getUser();

    }


    /**
     * 测试controller中getOne方法
     * 没有设置模拟数据，执行Mock对象的默认逻辑，
     * 日志：Body =
     *
     * @throws Exception
     */
    @Test
    public void getOneTest() throws Exception {
        // 发送请求并打印响应
        mockMvc.perform(MockMvcRequestBuilders
                        //构造请求
                        .get("/family/farm/demo/one")
                        //设置返回值类型
                        .accept(MediaType.APPLICATION_JSON)
                )
                //添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                //添加一个结果处理器，此处打印整个响应结果信息
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * 测试controller中getUserByIndex方法
     * 模拟userService中getUserList方法
     *
     * @throws Exception
     */
    @Test
    public void getUserByIndexTest() throws Exception {
        Mockito.when(userService.getUserList()).thenReturn(List.of(new User(3, "模拟Service的返回数据-3")));
        mockMvc.perform(MockMvcRequestBuilders
                        //构造请求
                        .get("/family/farm/demo/user/0")
                        //设置返回值类型
                        .accept(MediaType.APPLICATION_JSON)
                )
                //添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                //添加一个结果处理器，此处打印整个响应结果信息
                .andDo(MockMvcResultHandlers.print());

    }


    /**
     * 测试controller中getUserInfo方法
     * 模拟userService中getUserByIndex方法
     * <p>
     * 参数设置成Mockito.anyInt()时，
     * 测试时使用任意参数（测试时执行userService.getUserByIndex方法使用任意参数），
     * 日志：Body = {"id":3,"name":"模拟Service.getUserByIndex方法的返回值-3"}
     * <p>
     * 测试时使用的参数:测试时执行userService.getUserByIndex方法使用的参数
     * 参数设置成具体数据：Mock对象默认不执行真实方法，可以设置任意数据，如0，1，2，3...
     * <p>
     * 参数设置成具体数据(如：1)时，
     * 测试时使用的参数与设置的参数一致，设置的模拟有效，日志：Body = {"id":3,"name":"模拟Service.getUserByIndex方法的返回值-3"}
     * 测试时使用的参数与设置的参数不一致,相当于没设置模拟，执行Mock对象的默认值，日志：Body =
     *
     * @throws Exception
     */

    @Test
    public void getUserInfoTest() throws Exception {
        // 模拟 Service 返回有效数据
//        Mockito.when(userService.getUserByIndex(Mockito.anyInt())).thenReturn(new User(3, "模拟Service.getUserByIndex方法的返回值-3"));
        Mockito.when(userService.getUserByIndex(11)).thenReturn(new User(3, "模拟Service.getUserByIndex方法的返回值-3"));

        mockMvc.perform(MockMvcRequestBuilders
                        //构造请求
                        .get("/family/farm/demo/user/info")
                        //设置返回值类型
                        .accept(MediaType.APPLICATION_JSON)
                        //添加请求参数
                        .param("index", "0")
                )
                //添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                //添加一个结果处理器，此处打印整个响应结果信息
                .andDo(MockMvcResultHandlers.print());

    }


    /**
     * 测试controller中getUserList方法
     * 设置模拟userService中getUserList方法
     *
     * @throws Exception
     */
    @Test
    public void getUserListTest() throws Exception {
        Mockito.when(userService.getUserList()).thenReturn(List.of(new User(3, "模拟Service.getUserByIndex方法的返回值-3")));
        mockMvc.perform(MockMvcRequestBuilders
                        //构造请求
                        .get("/family/farm/demo/list")
                        //设置返回值类型
                        .accept(MediaType.APPLICATION_JSON)
                )
                //添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 验证中文字段值，因为Body = []，所以这个验证报错
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(3))
                //添加一个结果处理器，此处打印整个响应结果信息
                .andDo(MockMvcResultHandlers.print());

    }

}
