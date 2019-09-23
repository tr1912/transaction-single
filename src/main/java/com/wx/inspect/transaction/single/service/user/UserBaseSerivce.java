package com.wx.inspect.transaction.single.service.user;

import com.wx.inspect.transaction.single.po.TbUserBasePo;

public interface UserBaseSerivce {

    /**
     * 查询客户信息
     * @param userName
     * @return
     */
    TbUserBasePo findUserByName(String userName);

    /**
     * 注册用户
     * @param tbUserBasePo
     * @return
     */
    boolean registUser(TbUserBasePo tbUserBasePo);
}
