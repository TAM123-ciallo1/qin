package com.atguigu.mqtt.service;

import com.atguigu.mqtt.gateway.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//_________4-1
@Component
public class MqttMessageSender {

    @Autowired
    private MqttGateway mqttGateway ;

    public void sendMsg(String topic , String msg) {
        mqttGateway.sendMsgToMqtt(topic , msg);
    }

    public void sendMsg(String topic , int qos ,  String msg) {
        mqttGateway.sendMsgToMqtt(topic , qos ,  msg);
    }

}
