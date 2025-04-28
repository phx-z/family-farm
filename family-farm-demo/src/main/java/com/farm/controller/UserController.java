package com.farm.controller;


import com.farm.entity.User;
import com.farm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Tag(name = "用户模块", description = "用户的添加、查询与取消")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/user")
    @Operation(summary = "查询用户信息", description = "这里固定获取第一个用户，无需参数", responses = {
            @ApiResponse(responseCode = "200", description = "成功",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "用户不存在")
    })
    public User getUser() {
        return userService.getUser();
    }


    @GetMapping("/one")
    @Operation(summary = "查询用户信息", description = "这里固定获取第一个用户，无需参数", responses = {
            @ApiResponse(responseCode = "200", description = "成功",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "用户不存在")
    })
    public User getOne() {
        return userService.getUser();
    }


    @GetMapping("/list")
    @Operation(summary = "查询用户列表", description = "返回所有用户信息，无需参数")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/user/{index}")
    @Operation(summary = "查询用户信息", description = "需提供用户列表的INDEX")
    public User getUserByIndex(
            @Parameter(name = "index", description = "列表下标", example = "1", required = true)
            @PathVariable int index
    ) {
        return userService.getUserList().get(index);
    }

    @GetMapping("/user/info")
    @Operation(summary = "查询用户信息", description = "需提供用户列表的INDEX")
    public User getUserInfo(
            @Parameter(name = "index", description = "列表下标", example = "1", required = true)
            int index
    ) {
        return userService.getUserByIndex(index);
    }

}
