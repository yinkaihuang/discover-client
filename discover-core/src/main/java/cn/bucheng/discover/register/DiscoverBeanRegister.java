package cn.bucheng.discover.register;

import cn.bucheng.discover.annotation.DiscoverClientEnable;
import cn.bucheng.discover.util.BeanRegisterUtils;
import cn.bucheng.discover.util.EnvironmentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.eureka.RibbonEurekaAutoConfiguration;
import org.springframework.cloud.zookeeper.ZookeeperAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.RibbonZookeeperAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClientConfiguration;
import org.springframework.cloud.zookeeper.discovery.dependency.DependencyFeignClientAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.dependency.DependencyRestTemplateAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.dependency.DependencyRibbonAutoConfiguration;
import org.springframework.cloud.zookeeper.discovery.watcher.DependencyWatcherAutoConfiguration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperAutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistryAutoConfiguration;
import org.springframework.cloud.zookeeper.support.CuratorServiceDiscoveryAutoConfiguration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author ：yinchong
 * @create ：2019/8/21 10:46
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
public class DiscoverBeanRegister implements ImportBeanDefinitionRegistrar {

    public static final String REGISTER_TYPE = "register.type";
    public static final String EUREKA = "EUREKA";
    public static final String ZOOKEEPER = "ZOOKEEPER";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        if (!annotationMetadata.hasAnnotation(DiscoverClientEnable.class.getName()))
            return;
        String registerType = EnvironmentUtils.getValue(REGISTER_TYPE);
        if (Strings.isBlank(registerType)) {
            log.error("not get register.type from environment");
            throw new RuntimeException("please set register.type");
        }
        registerType = registerType.toUpperCase();
        switch (registerType) {
            case EUREKA:
                log.info("register use eureka");
                registerEureka(registry);
                break;
            case ZOOKEEPER:
                log.info("register use zookeeper");
                registerZookeeper(registry);
                break;
        }
    }

    private void registerEureka(BeanDefinitionRegistry registry) {
        BeanRegisterUtils.registerBeanDefinition(registry, EurekaClientAutoConfiguration.class.getName(), EurekaClientAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, RibbonEurekaAutoConfiguration.class.getName(), RibbonEurekaAutoConfiguration.class);
    }

    private void registerZookeeper(BeanDefinitionRegistry registry) {
        BeanRegisterUtils.registerBeanDefinition(registry, ZookeeperDiscoveryAutoConfiguration.class.getName(), ZookeeperDiscoveryAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, ZookeeperDiscoveryClientConfiguration.class.getName(), ZookeeperDiscoveryClientConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, CuratorServiceDiscoveryAutoConfiguration.class.getName(), CuratorServiceDiscoveryAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, ZookeeperServiceRegistryAutoConfiguration.class.getName(), ZookeeperServiceRegistryAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, ZookeeperAutoConfiguration.class.getName(), ZookeeperAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, ZookeeperAutoServiceRegistrationAutoConfiguration.class.getName(), ZookeeperAutoServiceRegistrationAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, DependencyRibbonAutoConfiguration.class.getName(), DependencyRibbonAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, DependencyFeignClientAutoConfiguration.class.getName(), DependencyFeignClientAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, DependencyRestTemplateAutoConfiguration.class.getName(), DependencyRestTemplateAutoConfiguration.class);
        BeanRegisterUtils.registerBeanDefinition(registry, DependencyWatcherAutoConfiguration.class.getName(), DependencyWatcherAutoConfiguration.class);
        //这里如果抽取出来跟踪链是无法正常运行
        BeanRegisterUtils.registerBeanDefinition(registry, RibbonZookeeperAutoConfiguration.class.getName(), RibbonZookeeperAutoConfiguration.class);
    }
}
