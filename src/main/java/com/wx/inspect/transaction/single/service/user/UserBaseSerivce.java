package com.wx.inspect.transaction.single.service.user;

import com.wx.inspect.transaction.single.po.TbUserBasePo;

public interface UserBaseSerivce {

    /**
     *
     * @param userName
     * @return
     */
    TbUserBasePo findUserByName(String userName);
}
