package com.atguigu.mqtt.config;
//硬件设备向 MQTT 代理服务器发送消息，然后由 Spring 应用接收
import com.atguigu.mqtt.domain.MqttConfigurationProperties;
import com.atguigu.mqtt.handler.ReceiverMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
//建立 MQTT 订阅连接
//配置并创建 MQTT 客户端，订阅指定主题（如atguigu/iot/lamp/line），实时监听 MQTT 服务器发布的消息。
//消息接收与转换
//接收到消息后，将其从 MQTT 协议格式转换为 Spring Integration 的Message对象，并通过消息通道传递。
//消息处理与业务逻辑集成
//将转换后的消息路由到自定义的消息处理器（ReceiverMessageHandler），执行业务逻辑（如更新设备状态到数据库）。
@Configuration
public class MqttInboundConfiguration {

    @Autowired
    private MqttConfigurationProperties mqttConfigurationProperties ;
//注入mqtt配置信息
    @Autowired
    private MqttPahoClientFactory mqttPahoClientFactory ;
//注入工厂，前一项的工厂
    @Autowired
    private ReceiverMessageHandler receiverMessageHandler ;
//注入消息处理器
    // 消息通道
    @Bean
    public MessageChannel messageInboundChannel() {
        return new DirectChannel();
    }
//创建连接消息通道（单线程同步传递消息）
    // 配置入站适配器，作用：设置订阅主题，以及指定消息的相关属性
    @Bean
    public MessageProducer messageProducer() {// 绑定到输入通道
        // public MqttPahoMessageDrivenChannelAdapter(String url, String clientId, MqttPahoClientFactory clientFactory,
        //			String... topic)
        //创建MQTT消息驱动适配器，指定服务器地址、订阅客户端ID、客户端工厂和订阅主题
        MqttPahoMessageDrivenChannelAdapter mqttPahoMessageDrivenChannelAdapter = new MqttPahoMessageDrivenChannelAdapter(
                //这段代码是Spring Integration的一部分，用于配置一个MQTT消息驱动的通道适配器
                //这个适配器允许Spring Integration框架通过MQTT协议发送和接收消息
                mqttConfigurationProperties.getUrl() ,
                mqttConfigurationProperties.getSubClientId() ,
                mqttPahoClientFactory ,
                mqttConfigurationProperties.getSubTopic().split(",")
                // 将逗号分隔的主题字符串拆分为数组
        ) ;

        mqttPahoMessageDrivenChannelAdapter.setQos(1);
        //MQTT 协议定义的消息传输质量等级，确保消息在网络不稳定时的可靠性
        mqttPahoMessageDrivenChannelAdapter.setConverter(new DefaultPahoMessageConverter());
        //DefaultPahoMessageConverter 是提供的默认 MQTT 消息转换器
        // 将 MQTT 消息（MqttMessage）转换为Message对象。消息驱动适配器！！！！设置转换！！！（不执行paho消息转换器
        mqttPahoMessageDrivenChannelAdapter.setOutputChannel(messageInboundChannel());
        // 设置输出通道，，，，，messageInboundChannel：由DirectChannel实现的同步通道，适配器将转换后的消息发送到此通道，
        // 后续由@ServiceActivator绑定的处理器消费。
        return mqttPahoMessageDrivenChannelAdapter ;
    }

    @Bean
    @ServiceActivator(inputChannel = "messageInboundChannel")
    //将消息通道与消息处理逻辑绑定
    //@ServiceActivator主要作用是定义一个消息处理器，将接收到的消息路由到指定的方法进行业务处理
    public MessageHandler messageHandler() {
        return receiverMessageHandler ;
    }
//声明这是一个服务激活器组件，从 "messageInboundChannel" 通道接收消息，消息到达后会触发该处理器执行
}
