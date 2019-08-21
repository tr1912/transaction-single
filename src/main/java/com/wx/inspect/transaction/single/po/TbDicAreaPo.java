package com.wx.inspect.transaction.single.po;

/**
 * 地区表
 * 
 * @author wx
 * @date 2019/08/20
 */ 
public class TbDicAreaPo {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */ 
    private Integer id;

    /**
     * 行政区划代码
     */ 
    private Integer code;

    /**
     * 父级行政区划代码
     */ 
    private Integer pcode;

    /**
     * 区域名称
     */ 
    private String name;

    /**
     * 首字母拼音
     */ 
    private String initials;

    /**
     * 深度（1：省  2：市  3：区县  4：乡镇街道）
     */ 
    private Byte depth;

    public Integer getId(){return id;}

    public void setId(Integer id){this.id=id;}

    public Integer getCode(){return code;}

    public void setCode(Integer code){this.code=code;}

    public Integer getPcode(){return pcode;}

    public void setPcode(Integer pcode){this.pcode=pcode;}

    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public String getInitials(){return initials;}

    public void setInitials(String initials){this.initials=initials;}

    public Byte getDepth(){return depth;}

    public void setDepth(Byte depth){this.depth=depth;}
}
