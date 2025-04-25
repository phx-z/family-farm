package com.farm.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName User
 * @Author phx
 * @Date 2025/4/24 19:20
 * @Description TODO
 */

@Data
@AllArgsConstructor
public class User {
    @Schema(description = "用户ID", example = "1")
    private int id;
    @Schema(description = "用户名称", example = "张三")
    private String name;
}
