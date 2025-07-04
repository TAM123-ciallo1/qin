package com.atguigu.mqtt.test;

import com.atguigu.mqtt.MqttClientApplication;
import com.atguigu.mqtt.service.MqttMessageSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MqttClientApplication.class)
public class MqttMessageSenderTest {

    @Autowired
    private MqttMessageSender mqttMessageSender ;

    @Test
    public void sendToMsg() {
        mqttMessageSender.sendMsg("java/c" , "hello mqtt spring boot...");
    }

}
