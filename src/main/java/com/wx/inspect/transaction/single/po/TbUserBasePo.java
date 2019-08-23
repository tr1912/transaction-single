package com.wx.inspect.transaction.single.po;

import lombok.Data;

import java.util.Date;

/**
 * 
 * 
 * @author wx
 * @date 2019/08/20
 */
@Data
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

    /**
     * 邮箱
     */
    private String email;

}
