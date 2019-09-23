package com.wx.inspect.transaction.single.service.user.impl;

import com.wx.inspect.transaction.single.mapper.TbUserLogsMapper;
import com.wx.inspect.transaction.single.po.TbUserBasePo;
import com.wx.inspect.transaction.single.po.TbUserLogsPo;
import com.wx.inspect.transaction.single.service.user.UserLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserLogsServiceImpl implements UserLogsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbUserLogsMapper tbUserLogsMapper;

    @Override
    public boolean loggerUserlog(TbUserBasePo userBasePo, String log) {
        TbUserLogsPo userLogsPo = new TbUserLogsPo();

        userLogsPo.setUserId(userBasePo.getId());
        userLogsPo.setLogText(log);
        userLogsPo.setCreateTime(new Date());
        userLogsPo.setType((byte)1);
        userLogsPo.setValue("1");
        userLogsPo.setIsDel((byte)1);

        int insert = tbUserLogsMapper.insert(userLogsPo);

        if (insert>0){
            logger.info("添加登录日志");
            return true;
        }else {
            return false;
        }
    }
}
