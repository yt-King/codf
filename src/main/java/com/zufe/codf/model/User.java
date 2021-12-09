package com.zufe.codf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 应涛
 * @date 2021/8/27
 * @function：用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    int id;
    String userName;
    String userPass;
    String phone;
}
