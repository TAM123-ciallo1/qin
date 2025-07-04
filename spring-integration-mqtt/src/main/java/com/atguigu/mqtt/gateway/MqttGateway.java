package com.atguigu.mqtt.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
//______________1-3
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    public abstract void sendMsgToMqtt(@Header(value = MqttHeaders.TOPIC) String topic , String payload) ;

    public abstract void sendMsgToMqtt(@Header(value = MqttHeaders.TOPIC) String topic , @Header(value = MqttHeaders.QOS) int qos ,  String payload) ;
//String payload指消息的负载内容，通常为 JSON、XML 或二进制数据的字符串表示
}
