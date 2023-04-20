package com.twelvet.framework.utils.annotation.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: Excel注解集
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels {

	Excel[] value();

}
