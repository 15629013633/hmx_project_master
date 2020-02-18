package com.hmx.annotations;

import java.lang.annotation.*;

/**
 * 用于方法之上
 */
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedToken {

    String value();
}
