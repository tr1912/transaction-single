package com.wx.inspect.transaction.single.po;

/**
 * 
 * 
 * @author wx
 * @date 2019/08/20
 */ 
public class TbUserAuthPo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */ 
    private Integer id;

    /**
     * 用户ID
     */ 
    private Integer userId;

    /**
     * 权限ID
     */ 
    private Integer authId;

    /**
     * 是否标记删除
     */ 
    private Byte isDel;

    public Integer getId(){return id;}

    public void setId(Integer id){this.id=id;}

    public Integer getUserId(){return userId;}

    public void setUserId(Integer userId){this.userId=userId;}

    public Integer getAuthId(){return authId;}

    public void setAuthId(Integer authId){this.authId=authId;}

    public Byte getIsDel(){return isDel;}

    public void setIsDel(Byte isDel){this.isDel=isDel;}
}
