package com.wx.inspect.transaction.single.service.user.impl;

import com.wx.inspect.transaction.single.mapper.TbUserBaseMapper;
import com.wx.inspect.transaction.single.po.TbUserBasePo;
import com.wx.inspect.transaction.single.service.user.UserBaseSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserBaseSerivceImpl implements UserBaseSerivce {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbUserBaseMapper tbUserBaseMapper;

    @Override
    public TbUserBasePo findUserByName(String userName) {
        return tbUserBaseMapper.findUserByName(userName);
    }

    @Override
    public boolean registUser(TbUserBasePo tbUserBasePo) {
        tbUserBasePo.setArea("");
        tbUserBasePo.setIsDel((byte)0);
        tbUserBasePo.setMobile("");
        tbUserBasePo.setCreateTime(new Date());
        tbUserBasePo.setAuth(1);
        int insert = tbUserBaseMapper.insert(tbUserBasePo);
        if (insert>0){
            return true;
        }else {
            logger.info("插入失败");
            return false;
        }
    }
}
