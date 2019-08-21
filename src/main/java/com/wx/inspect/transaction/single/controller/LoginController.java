package com.wx.inspect.transaction.single.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    /**
     * 登录页
     * @return
     */
    @RequestMapping(value = {"/","login"})
    public ModelAndView login(){
        ModelAndView model =new ModelAndView("login");
        return model;
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping(value = "regist")
    public ModelAndView regist(){
        ModelAndView model =new ModelAndView("register");
        return model;
    }

    /**
     * 重置密码
     * @return
     */
    @RequestMapping(value = "recoverpw")
    public ModelAndView recoverpw(){
        ModelAndView model =new ModelAndView("recoverpw");
        return model;
    }


    @RequestMapping(value = "index")
    public ModelAndView index(){
        ModelAndView model =new ModelAndView("index");
        return model;
    }

}
