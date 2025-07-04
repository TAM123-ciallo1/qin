package com.atguigu.mqtt.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
// 导入查询条件构造器类
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.mqtt.domain.TbSwitch;
import com.atguigu.mqtt.service.TbSwitchService;
import com.atguigu.mqtt.mapper.TbSwitchMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 针对表【tb_lamp】的数据库操作Service实现
 */
@Service
public class TbSwitchServiceImpl extends ServiceImpl<TbSwitchMapper, TbSwitch> implements TbSwitchService {

    @Override
    public void updateLampOnlineStatus(String payload) {
        Map<String, Object> map = JSON.parseObject(payload, Map.class);
        String deviceId = map.get("deviceId").toString();
        Integer status = Integer.parseInt(map.get("online").toString());

        // 根据设备的id查询设备信息
        LambdaQueryWrapper<TbSwitch> queryWrapper = new LambdaQueryWrapper<>(); // 修正类名
        queryWrapper.eq(TbSwitch::getDeviceid, deviceId);

        TbSwitch tbSwitch = this.getOne(queryWrapper); // 修正参数

        if (tbSwitch == null) {
            tbSwitch = new TbSwitch();
            tbSwitch.setDeviceid(deviceId);
            tbSwitch.setStatus(status);
            tbSwitch.setCreateTime(new Date());
            tbSwitch.setUpdateTime(new Date());
            this.save(tbSwitch);
        } else {
            tbSwitch.setStatus(status);
            tbSwitch.setUpdateTime(new Date());
            this.updateById(tbSwitch);
        }
    }
}