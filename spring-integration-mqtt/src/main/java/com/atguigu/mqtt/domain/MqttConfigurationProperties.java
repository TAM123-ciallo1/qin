package com.atguigu.mqtt.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.mqtt")
//将类与配置文件中以spring.mqtt为前缀的属性绑定（如application.yml或application.properties）。
public class MqttConfigurationProperties {

    private String username ;
    private String password ;
    private String url ;
    private String subClientId ;
    private String subTopic ;
    private String pubClientId ;

}
