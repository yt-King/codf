package com.zufe.codf.controller;

import com.zufe.codf.model.Calculator;
import com.zufe.codf.service.CalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 应涛
 * @date 2021/9/7
 * @function： 运算器控制
 */
@RestController
@RequestMapping(value = "/api/codff")
public class CalController {
    @Autowired
    private CalService calService;

    @PostMapping("/operation")
    public Map insert(@RequestBody Calculator calculator) {
        return calService.operation(calculator);
    }

    @RequestMapping("/delCookie")
    public void delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            System.out.println("没有cookie==============");
        } else {
            for (Cookie cookie : cookies) {
                System.out.println("cookie.getName() = " + cookie.getName());
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    System.out.println("被删除的cookie名字为:" + cookie.getName());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}

