package com.wx.inspect.transaction.single.po;

import java.util.Date;

/**
 * 
 * 
 * @author wx
 * @date 2019/08/20
 */ 
public class TbUserBasePo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */ 
    private Integer id;

    /**
     * 姓名
     */ 
    private String userName;

    /**
     * 密码
     */ 
    private String password;

    /**
     * 权限
     */ 
    private Integer auth;

    /**
     * 删除标记
     */ 
    private Byte isDel;

    /**
     * 电话号码
     */ 
    private String mobile;

    /**
     * 区域
     */ 
    private String area;

    /**
     * 创建时间
     */ 
    private Date createTime;

    public Integer getId(){return id;}

    public void setId(Integer id){this.id=id;}

    public String getUserName(){return userName;}

    public void setUserName(String userName){this.userName=userName;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password=password;}

    public Integer getAuth(){return auth;}

    public void setAuth(Integer auth){this.auth=auth;}

    public Byte getIsDel(){return isDel;}

    public void setIsDel(Byte isDel){this.isDel=isDel;}

    public String getMobile(){return mobile;}

    public void setMobile(String mobile){this.mobile=mobile;}

    public String getArea(){return area;}

    public void setArea(String area){this.area=area;}

    public Date getCreateTime(){return createTime;}

    public void setCreateTime(Date createTime){this.createTime=createTime;}
}
