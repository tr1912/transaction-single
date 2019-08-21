package com.wx.inspect.transaction.single.po;

/**
 * 
 * 
 * @author wx
 * @date 2019/08/20
 */ 
public class TbAuthPo {

    /**
     * ID
     */ 
    private Integer id;

    /**
     * 权限名称
     */ 
    private String authName;

    /**
     * 权限编码
     */ 
    private String authCode;

    /**
     * 类型
     */ 
    private Byte type;

    /**
     * 是否标记删除
     */ 
    private Byte isDel;

    public Integer getId(){return id;}

    public void setId(Integer id){this.id=id;}

    public String getAuthName(){return authName;}

    public void setAuthName(String authName){this.authName=authName;}

    public String getAuthCode(){return authCode;}

    public void setAuthCode(String authCode){this.authCode=authCode;}

    public Byte getType(){return type;}

    public void setType(Byte type){this.type=type;}

    public Byte getIsDel(){return isDel;}

    public void setIsDel(Byte isDel){this.isDel=isDel;}
}
