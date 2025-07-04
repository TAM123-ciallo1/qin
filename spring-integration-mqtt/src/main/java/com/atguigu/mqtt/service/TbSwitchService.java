package com.atguigu.mqtt.service;

import com.atguigu.mqtt.domain.TbSwitch;
import com.baomidou.mybatisplus.extension.service.IService;
//0
/**
* @author hly
* @description 针对表【tb_lamp】的数据库操作Service
* @createDate 2024-10-27 21:00:41
*/
public interface TbSwitchService extends IService<TbSwitch> {

    public abstract void updateLampOnlineStatus(String payload);
}
