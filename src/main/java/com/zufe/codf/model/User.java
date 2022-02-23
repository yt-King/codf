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

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getPhone() {
        return phone;
    }
}
