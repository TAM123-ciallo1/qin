package com.atguigu.mqtt.config;

import com.atguigu.mqtt.domain.MqttConfigurationProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
//____________3-1
@Configuration
public class MqttConfiguration {

    @Autowired
    private MqttConfigurationProperties mqttConfigurationProperties ;
//这行代码使用  @Autowired  注解自动注入  MqttConfigurationProperties  对象。
// 这个对象通常包含MQTT服务器的配置信息，如用户名、密码和URL等。
    //对象被绑定
    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        //Paho指Eclipse-Paho开源项目，这里是客户端工厂类
        DefaultMqttPahoClientFactory mqttPahoClientFactory = new DefaultMqttPahoClientFactory() ;
        //工厂实体化
        MqttConnectOptions options = new MqttConnectOptions() ;
        //工厂连接实体化
        options.setCleanSession(true);
        // 设置连接为"清洁会话"模式：
        // - true：断开连接后清除订阅和消息状态
        // - false：保留状态（适用于持久订阅）
        options.setUserName(mqttConfigurationProperties.getUsername());
        //设置服务器连接名，在domain中配置
        options.setPassword(mqttConfigurationProperties.getPassword().toCharArray());
        //设置连接密码
        options.setServerURIs(new String[]{ mqttConfigurationProperties.getUrl() } );
        //设置连接地址
        mqttPahoClientFactory.setConnectionOptions(options);
        //将连接与工厂相连
        return mqttPahoClientFactory ;
    }

}
