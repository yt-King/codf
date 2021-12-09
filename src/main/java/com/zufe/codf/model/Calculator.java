package com.zufe.codf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 应涛
 * @date 2021/9/7
 * @function：运算器实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calculator {
    String s;
    String input;
    String realA;
    String realW;
    int AEN;
    int WEN;
    int cyIn;
    String result;
}
