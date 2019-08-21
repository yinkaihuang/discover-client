package cn.bucheng.discover.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Objects;

/**
 * @author ：yinchong
 * @create ：2019/8/21 10:41
 * @description：
 * @modified By：
 * @version:
 */
public abstract class BeanRegisterUtils {

    /**
     * 注册类到spring的ioc容器中
     * @param registry ioc容器
     * @param beanName 注册ioc容器中的唯一标示
     * @param clazz 注册的类对象
     * @return 注册成功与否
     */
    public static boolean registerBeanDefinition(BeanDefinitionRegistry registry, String beanName, Class<?> clazz) {
        if (registry.containsBeanDefinition(beanName))
            return false;
        String[] candidates = registry.getBeanDefinitionNames();
        for (String candidate : candidates) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(candidate);
            if (Objects.equals(beanDefinition.getBeanClassName(), clazz.getName())) {
                return false;
            }
        }

        BeanDefinition annotationProcessor = BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition();
        registry.registerBeanDefinition(beanName, annotationProcessor);
        return true;
    }
}
