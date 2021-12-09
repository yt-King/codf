package com.zufe.codf.integration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 应涛
 * @date 2021/9/9
 * @function：
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private FileWriterGateway fileWriterGateway;

    @PostMapping("/test")
    public String sendMqtt(String filename,String data){
        fileWriterGateway.writeToFile( filename, data);
        return "OK";
    }
}