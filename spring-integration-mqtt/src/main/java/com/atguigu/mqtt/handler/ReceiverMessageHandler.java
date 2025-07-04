package com.atguigu.mqtt.handler;

import com.atguigu.mqtt.service.TbSwitchService;
import com.atguigu.mqtt.service.TbSwitchStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
//7-2,7-3
@Component
public class ReceiverMessageHandler implements MessageHandler {
//该类实现了 MessageHandler 接口，意味着它必须实现 handleMessage 方法来处理接收到的消息。
    @Autowired
    private TbSwitchService tbSwitchService ;

    @Autowired
    private TbSwitchStatusService tbSwitchStatusService ;

    @Override//Message对象是由框架自动创建和管理的
    public void handleMessage(Message<?> message) throws MessagingException {
        //它接受一个Message<?>类型参数，这表示它可以处理任何类型负载的消息。
        //MessagingException是 Spring Messaging 框架定义的异常类型，用于表示消息处理过程中发生的错误
        String payload = message.getPayload().toString();
        //获取消息的负载，并将其转换为字符串。
        MessageHeaders headers = message.getHeaders();
        //获取消息的头部信息。
        String topicName = headers.get("mqtt_receivedTopic").toString();
        //从消息头部获取 MQTT 主题名称
        if("atguigu/iot/switch/line".equals(topicName)) {
            tbSwitchService.updateLampOnlineStatus(payload) ;
            //如果接收到的消息主题是 它  则调用 tbLampService 的 updateLampOnlineStatus 方法来更新灯具的在线状态。
        }else if("atguigu/iot/switch/device/status".equals(topicName)) {
            tbSwitchStatusService.saveDeviceStatus(payload) ;
        }
    }

}
