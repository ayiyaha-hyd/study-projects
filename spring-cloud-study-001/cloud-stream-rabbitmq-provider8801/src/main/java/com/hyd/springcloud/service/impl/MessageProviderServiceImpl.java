package com.hyd.springcloud.service.impl;

import com.hyd.springcloud.service.MessageProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

@EnableBinding(Source.class)//定义消息的推送管道
public class MessageProviderServiceImpl implements MessageProviderService {

    @Autowired
    private MessageChannel output;
    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        //注意引入的是 org.springframework.messaging.support.MessageBuilder
        output.send(MessageBuilder.withPayload(serial).build());
        return "message send()~";
    }
}
