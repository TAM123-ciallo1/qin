package com.atguigu.mqtt.config;

import com.atguigu.mqtt.domain.MqttConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
//软件应用向 MQTT 代理服务器发送消息的过程
@Configuration
public class MqttOutboundConfiguration {

    @Autowired
    private MqttConfigurationProperties mqttConfigurationProperties ;

    @Autowired
    private MqttPahoClientFactory mqttPahoClientFactory ;
//MqttPahoClientFactory：是 Spring Integration 为 MQTT 提供的客户端工厂类。
    // 消息通道
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel() ;
    }
    //MessageChannel：是 Spring 消息框架中的消息通道接口
//DirectChannel是消息通道实现，它将 MQTT 适配器接收到的消息直接传递给处理器，发送线程会阻塞直至处理完成。
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    //@ServiceActivator用于将方法或 Bean 标记为消息处理器
    //将处理器绑定到 mqttOutboundChannel 通道，接收并处理消息。
    public MessageHandler mqttOutboundMessageHandler() {
        //MqttPahoMessageHandler：是 Spring Integration 提供的用于处理 MQTT 消息发送的处理器类。
        // public MqttPahoMessageHandler(String url, String clientId, MqttPahoClientFactory clientFactory)
        MqttPahoMessageHandler mqttPahoMessageHandler = new MqttPahoMessageHandler(mqttConfigurationProperties.getUrl() ,
        //MqttPahoMessageHandler：Spring Integration 提供的 MQTT 消息处理器，负责发送消息到 MQTT 代理
        mqttConfigurationProperties.getPubClientId() , mqttPahoClientFactory) ;
        //指定代理的地址，发布消息的客户端，客户端工厂
        mqttPahoMessageHandler.setDefaultQos(0);
        mqttPahoMessageHandler.setDefaultTopic("default");
        //setDefaultTopic("default")：未指定主题时使用的默认主题。
        mqttPahoMessageHandler.setAsync(true);
        //setAsync(true)：开启异步发送模式，提高吞吐量，但不等待服务器确认
        return mqttPahoMessageHandler ;
    }
}
