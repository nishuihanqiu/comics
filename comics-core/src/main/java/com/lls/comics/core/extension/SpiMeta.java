package com.lls.comics.core.extension;

import java.lang.annotation.*;

/************************************
 * SpiMeta
 * @author liliangshan
 * @date 2019-01-31
 ************************************/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpiMeta {

    String name() default "";

}
