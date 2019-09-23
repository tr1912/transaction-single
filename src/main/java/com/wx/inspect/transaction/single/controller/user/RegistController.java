package com.wx.inspect.transaction.single.controller.user;

import com.wx.inspect.transaction.single.base.MessageResult;
import com.wx.inspect.transaction.single.po.TbUserBasePo;
import com.wx.inspect.transaction.single.service.user.UserBaseSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/regist")
@Controller
public class RegistController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserBaseSerivce userBaseSerivce;

    @RequestMapping("/regist")
    @ResponseBody
    public ModelAndView regist(TbUserBasePo tbUserBasePo) {
        ModelAndView model = new ModelAndView();

        try {
            boolean flag = userBaseSerivce.registUser(tbUserBasePo);
            if (flag){
                logger.info("注册成功");
                model.setViewName("index");
            }else {
                logger.info("注册失败，请联系管理员！");
                model.setViewName("login");
                model.addObject("message","注册失败，请联系管理员！");
            }
        } catch (Exception e) {
            logger.error("注册出现异常！", e);
            model.setViewName("login");
            model.addObject("message","注册出现异常");
        }
        return model;
    }
}
