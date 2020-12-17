package com.wx.inspect.transaction.single.controller.user;

import com.wx.inspect.transaction.single.base.BaseController;
import com.wx.inspect.transaction.single.base.MessageResult;
import com.wx.inspect.transaction.single.po.TbUserBasePo;
import com.wx.inspect.transaction.single.service.user.UserBaseSerivce;
import com.wx.inspect.transaction.single.service.user.UserLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/login")
@Controller
public class LoginController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserBaseSerivce userBaseSerivce;

    @Autowired
    private UserLogsService userLogsService;

    /**
     * 登录判断方法
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public MessageResult login(String userName, String password) {

        TbUserBasePo userBasePo = userBaseSerivce.findUserByName(userName);
        if (userBasePo != null && !StringUtils.isEmpty(userBasePo.getUserName())) {
            if (!password.equals(userBasePo.getPassword())) {
                return MessageResult.build("0", "用户名或密码错误！");
            }
            setAccount(userBasePo);
            userLogsService.loggerUserlog(userBasePo, "login");
        } else {
            return MessageResult.build("0", "用户名或密码错误！");
        }
        return MessageResult.build("1", "登录成功");
    }
}
