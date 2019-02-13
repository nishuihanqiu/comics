package com.lls.comics.core.extension;

import java.lang.annotation.*;

/************************************
 * Spi
 * @author liliangshan
 * @date 2019-01-31
 ************************************/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Spi {

    Scope scope() default Scope.PROTOTYPE;

}
