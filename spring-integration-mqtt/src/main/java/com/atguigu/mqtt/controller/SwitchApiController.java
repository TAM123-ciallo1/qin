package com.atguigu.mqtt.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.mqtt.service.MqttMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
//通过 MQTT 协议向物联网设备（如灯具）发送状态控制指令
@RestController
@RequestMapping(value = "/api/lamp")
public class SwitchApiController {
//__________7-1
    @Autowired
    private MqttMessageSender mqttMessageSender ;
//路径：/api/lamp/{deviceId}/{status}，请求方法：GET
    @GetMapping(value = "/{deviceId}/{status}")
    public String sendStatusLampMsg(@PathVariable(value = "deviceId") String deviceId , @PathVariable(value = "status") Integer status ) {
        Map<String , Object> map = new HashMap<>() ;
        //创建一个  HashMap  来存储设备ID和状态。• 使用  JSON.toJSONString(map)  将Map转换为JSON字符串。• 调用  mqttMessageSender  的  sendMsg  方法发送消息到指定的主题（  atguigu/iot/lamp/server/status  ）。• 返回字符串  ok  表示操作成功
        map.put("deviceId",deviceId ) ;
        //
        map.put("status" , status) ;
        String json = JSON.toJSONString(map);
        mqttMessageSender.sendMsg("atguigu/iot/switch/server/status" , json);
        // 调用  mqttMessageSender  的  sendMsg  方法发送消息到指定的主题（  atguigu/iot/switch/server/status  ）
        return "ok" ;
    }
}    