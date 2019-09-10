package cn.bucheng.discover.annotation;

import cn.bucheng.discover.register.DiscoverBeanImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：yinchong
 * @create ：2019/8/21 10:38
 * @description：
 * @modified By：
 * @version:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(DiscoverBeanImportSelector.class)
public @interface EnableRegisterClient {
}
