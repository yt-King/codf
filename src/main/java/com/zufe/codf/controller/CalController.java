package com.zufe.codf.controller;

import com.zufe.codf.model.Calculator;
import com.zufe.codf.service.CalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 应涛
 * @date 2021/9/7
 * @function： 运算器控制
 */
@RestController
@RequestMapping(value="/codf")
public class CalController {
    @Autowired
    private CalService calService;
    @PostMapping("/operation")
    public Map insert(@RequestBody Calculator calculator)  {
        return calService.operation(calculator);
    }
}

