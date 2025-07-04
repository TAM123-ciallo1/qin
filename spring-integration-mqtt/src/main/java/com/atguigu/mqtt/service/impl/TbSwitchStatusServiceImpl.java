package com.atguigu.mqtt.service.impl;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.mqtt.domain.TbSwitchStatus;
import com.atguigu.mqtt.service.TbSwitchStatusService;
import com.atguigu.mqtt.mapper.TbSwitchStatusMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author hly
* 针对表【tb_Switch_status】的数据库操作Service实现
*
*/
@Service
public class TbSwitchStatusServiceImpl extends ServiceImpl<TbSwitchStatusMapper, TbSwitchStatus>
    implements TbSwitchStatusService {

    @Override
    public void saveDeviceStatus(String payload) {
        Map<String , Object> map = JSON.parseObject(payload, Map.class);
        String deviceId = map.get("deviceId").toString();
        Integer status = Integer.parseInt(map.get("status").toString());
        TbSwitchStatus tbSwitchStatus = new TbSwitchStatus() ;
        tbSwitchStatus.setDeviceid(deviceId);
        tbSwitchStatus.setStatus(status);
        tbSwitchStatus.setCreateTime(new Date());
        this.save(tbSwitchStatus) ;
    }
}




