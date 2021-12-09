package com.zufe.codf.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author 应涛
 * @date 2021/9/9
 * @function：
 */
@MessagingGateway(defaultReplyChannel = "textInChannel") //声明消息网关
public interface FileWriterGateway {
    void writeToFile(@Header(FileHeaders.FILENAME) String filename,String data);
}
