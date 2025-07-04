package com.atguigu.mqtt.service;

import com.atguigu.mqtt.domain.TbSwitchStatus;
import com.baomidou.mybatisplus.extension.service.IService;
//0
/**
* @author hly
* @description 针对表【tb_lamp_status】的数据库操作Service
* @createDate 2024-10-27 21:00:41
*/
public interface TbSwitchStatusService extends IService<TbSwitchStatus> {

    public abstract void saveDeviceStatus(String payload);
}
