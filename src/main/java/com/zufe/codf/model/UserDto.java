package com.zufe.codf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 应涛
 * @date 2021/8/27
 * @function：用于登录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    String userName;
    String userPass;
    String code;
}
