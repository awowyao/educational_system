package com.cwy.educationaladministrationsystem.controller;

import com.cwy.educationaladministrationsystem.config.rabbitmqConfig;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component
public class ReceiveHandler {
    @RabbitListener(queues = {rabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(@Payload String body, Message message, Channel channel){
        System.out.println(message);
        System.out.println(channel);
        System.out.println("QUEUE_INFORM_EMAIL msg"+body);
    }
}
