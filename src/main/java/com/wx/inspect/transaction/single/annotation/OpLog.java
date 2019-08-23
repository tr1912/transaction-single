package com.wx.inspect.transaction.single.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OpLog {

  // 模块
  String module();
  // 类型
  String opType();
  // 级别，默认一般
  String level() default "0";
  // 需要记录的key
  String key();
}