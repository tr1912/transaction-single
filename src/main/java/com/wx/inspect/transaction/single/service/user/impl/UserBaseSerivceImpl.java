package com.wx.inspect.transaction.single.service.user.impl;

import com.wx.inspect.transaction.single.mapper.TbUserBaseMapper;
import com.wx.inspect.transaction.single.po.TbUserBasePo;
import com.wx.inspect.transaction.single.service.user.UserBaseSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBaseSerivceImpl implements UserBaseSerivce {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbUserBaseMapper tbUserBaseMapper;

    @Override
    public TbUserBasePo findUserByName(String userName) {
        return tbUserBaseMapper.findUserByName(userName);
    }
}
