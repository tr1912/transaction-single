package com.wx.inspect.transaction.single.base;

import com.wx.inspect.transaction.single.po.TbUserBasePo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class BaseController {

    private static final String ACCOUNT = "account";

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public TbUserBasePo getAccount() {
        HttpSession session = getRequest().getSession();
        return (TbUserBasePo) session.getAttribute(ACCOUNT);
    }

    public void setAccount(TbUserBasePo account) {
        HttpSession session = getRequest().getSession();
        if (account != null) {
            session.setAttribute(ACCOUNT, account);
            //session过期时间设置，以秒为单位，即在没有活动30分钟后，session将失效
            session.setMaxInactiveInterval(30 * 60);
        }
    }
}
