package com.farm.controller;


import com.farm.entity.User;
import com.farm.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

/**
 * @ClassName UserControllerTest
 * @Author phx
 * @Date 2025/4/25 14:39
 * @Description 测试controller
 *
 * <p>
 * Mock对象与Spy对象、thenReturn与doReturn的选择如下：
 * <p>
 * Mock对象与Spy对象选择：@Spy对象
 * 因测试中可能需要模拟某方法，也可能测试真实方法，所以使用@Spy对象，
 * thenReturn与doReturn选择：doReturn
 * 在使用@Spy对象时，thenReturn会触发真实方法，when子句执行，可能引发异常或副作用，
 * doReturn跳过真实方法，所以使用doReturn
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
 * thenReturn()会先执行真实方法（若存在）,，when子句执行，可能引发异常或副作用
 * doReturn()直接覆盖方法返回值，‌不触发真实方法‌,直接覆盖方法行为
 */

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 显式模拟 Service 层依赖
    @SpyBean
    private UserService userService;

    /**
     * 测试controller中getUser方法
     * 模拟userService.getUser方法的返回值，
     * Body = {"id":1,"name":"模拟Service的返回数据"}
     * <p>
     * 对于Spy对象，thenReturn与doReturn方法的区别在于thenReturn会先执行真实方法，即Mockito.when子句会执行，
     * debug如下：
     * Mockito.thenReturn:
     * -> Mockito.when
     * -> UserServiceImp中getUser方法
     * -> mockMvc.perform
     * -> UserController中getUser方法
     * -> andExpect
     * Mockito.doReturn:
     * -> Mockito.doReturn
     * -> mockMvc.perform
     * -> UserController中getUser方法
     * -> andExpect
     *
     * @throws Exception
     */
    @Test
    public void getUserTest() throws Exception {
        // 模拟 Service 返回有效数据
//        Mockito.when(userService.getUser()).thenReturn(new User(1, "模拟Service的返回数据"));
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

    }


    /**
     * 测试controller中getOne方法
     * 没有设置模拟数据，测试该方法的真实逻辑
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
                // 验证中文字段值，因为没有返回值，这个验证报错
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("用户名"))
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
        Mockito.doReturn(List.of(new User(3, "模拟Service的返回数据-3"))).when(userService).getUserList();
        mockMvc.perform(MockMvcRequestBuilders
                        //构造请求
                        .get("/family/farm/demo/user/1")
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
     * 参数设置成具体数据：
     * Spy对象doReturn不执行真实方法，可以设置任意数据，如0，1，2，3...
     * Spy对象thenReturn执行真实方法，when子句执行，所以参数受限制，比如，List中2个元素，参数只能设置成0，1，否则when子句执行异常
     * <p>
     * 参数设置成具体数据(如：1)时，
     * 测试时使用的参数与设置的参数一致，设置的模拟有效，日志：Body = {"id":3,"name":"模拟Service.getUserByIndex方法的返回值-3"}
     * 测试时使用的参数与设置的参数不一致,相当于没设置模拟，测试真实逻辑
     *
     * @throws Exception
     */

    @Test
    public void getUserInfoTest() throws Exception {
        // 模拟 Service 返回有效数据
//        Mockito.doReturn(new User(3, "模拟Service.getUserByIndex方法的返回值-3")).when(userService).getUserByIndex(Mockito.anyInt());
        Mockito.doReturn(new User(3, "模拟Service.getUserByIndex方法的返回值-3")).when(userService).getUserByIndex(0);

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
        Mockito.doReturn(List.of(new User(3, "模拟Service.getUserByIndex方法的返回值-3"))).when(userService).getUserList();
        mockMvc.perform(MockMvcRequestBuilders
                        //构造请求
                        .get("/family/farm/demo/list")
                        //设置返回值类型
                        .accept(MediaType.APPLICATION_JSON)
                )
                //添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 验证中文字段值，因为Body = []，所以这个验证报错
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(4))
                //添加一个结果处理器，此处打印整个响应结果信息
                .andDo(MockMvcResultHandlers.print());

    }
}
