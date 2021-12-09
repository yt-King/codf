package com.zufe.codf.service;

import com.zufe.codf.model.Calculator;
import com.zufe.codf.service.util.BinaryConversion;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应涛
 * @date 2021/9/7
 * @function：
 */
@Service
public class CalService {
    /**
     * ALU 运算
     * @param calculator
     * @return
     */
    public Map operation(Calculator calculator){
        Map map = new HashMap();
        if(calculator.getRealA()==null||calculator.getRealW()==null){
            if(calculator.getInput()==null){
                map.put("code",0);
                map.put("msg","输入不能为空");
                map.put("calculator",calculator);
                return map;
            }
            else if(calculator.getAEN()==1&&calculator.getWEN()!=1){ //A使能，W不使能
                calculator.setRealA(calculator.getInput());
                map.put("code",2);
                map.put("msg","A输入成功");
                map.put("calculator",calculator);
                return map;
            }
            else if(calculator.getAEN()!=1&&calculator.getWEN()==1){ //W使能，A不使能
                calculator.setRealW(calculator.getInput());
                map.put("code",2);
                map.put("msg","W输入成功");
                map.put("calculator",calculator);
                return map;
            }
            else if(calculator.getAEN()==1&&calculator.getWEN()==1){ //同时使能
                calculator.setRealA(calculator.getInput());
                calculator.setRealW(calculator.getInput());
                map.put("code",2);
                map.put("msg","A,W输入成功");
                map.put("calculator",calculator);
                return map;
            }
            else{
                map.put("code",0);
                map.put("msg","输入失败");
                map.put("calculator",calculator);
                return map;
            }
        }
        else{
            String aa=calculator.getRealA();
            String ww=calculator.getRealW();
            int a=Integer.valueOf(BinaryConversion.getHexToTen(aa));
            int w=Integer.valueOf(BinaryConversion.getHexToTen(ww));
            int c=calculator.getCyIn();
            map.put("code",1);
            map.put("msg","计算成功");
            switch (calculator.getS()){
                case "000"://加运算
                    calculator.setResult(Integer.toHexString((a+w)));
                    map.put("calculator",calculator);
                    break;
                case "001"://减运算
                    calculator.setResult(Integer.toHexString((a-w)));
                    map.put("calculator",calculator);
                    break;
                case "010"://或运算
                    calculator.setResult(Integer.toHexString((a|w)));
                    map.put("calculator",calculator);
                    break;
                case "011"://与运算
                    calculator.setResult(Integer.toHexString((a&w)));
                    map.put("calculator",calculator);
                    break;
                case "100"://进位加运算
                    calculator.setResult(Integer.toHexString((a+w+c)));
                    map.put("calculator",calculator);
                    break;
                case "101"://进位减运算
                    calculator.setResult(Integer.toHexString((a-w-c)));
                    map.put("calculator",calculator);
                    break;
                case "110"://A取反运算
                    String res=Integer.toHexString((~a));
                    res = res.substring(res.length()-2);
                    calculator.setResult(res);
                    map.put("calculator",calculator);
                    break;
                case "111"://A输出运算
                    calculator.setResult(Integer.toHexString((a)));
                    map.put("calculator",calculator);
                    break;
                default:
                    map.put("code",0);
                    map.put("msg","计算失败");
                    map.put("calculator",calculator);
            }
            return map;
        }
    }
}
