package com.wx.inspect.transaction.single.po;

import java.util.Date;

/**
 * 
 * 
 * @author wx
 * @date 2019/08/20
 */ 
public class TbUserLogsPo {

    /**
     * ID
     */ 
    private Integer id;

    /**
     * 用户ID
     */ 
    private Integer userId;

    /**
     * 日志内容
     */ 
    private String logText;

    /**
     * 创建时间
     */ 
    private Date createTime;

    /**
     * 类型
     */ 
    private Byte type;

    /**
     * 可用值
     */ 
    private String value;

    /**
     * 删除标记
     */ 
    private Byte isDel;

    public Integer getId(){return id;}

    public void setId(Integer id){this.id=id;}

    public Integer getUserId(){return userId;}

    public void setUserId(Integer userId){this.userId=userId;}

    public String getLogText(){return logText;}

    public void setLogText(String logText){this.logText=logText;}

    public Date getCreateTime(){return createTime;}

    public void setCreateTime(Date createTime){this.createTime=createTime;}

    public Byte getType(){return type;}

    public void setType(Byte type){this.type=type;}

    public String getValue(){return value;}

    public void setValue(String value){this.value=value;}

    public Byte getIsDel(){return isDel;}

    public void setIsDel(Byte isDel){this.isDel=isDel;}
}
