package com.wx.inspect.transaction.single.service.user;

import com.wx.inspect.transaction.single.po.TbUserBasePo;

public interface UserLogsService {

    /**
     * 计入logger
     * @param userBasePo
     * @param login
     * @return
     */
    boolean loggerUserlog(TbUserBasePo userBasePo, String login);
}
