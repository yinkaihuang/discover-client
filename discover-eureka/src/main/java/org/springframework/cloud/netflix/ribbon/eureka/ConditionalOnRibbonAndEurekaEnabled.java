package org.springframework.cloud.netflix.ribbon.eureka;

import com.netflix.discovery.EurekaClient;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalOnRibbonAndEurekaEnabled.OnRibbonAndEurekaEnabledCondition.class)
public @interface ConditionalOnRibbonAndEurekaEnabled {

    class OnRibbonAndEurekaEnabledCondition extends AllNestedConditions {

        public OnRibbonAndEurekaEnabledCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnClass(DiscoveryEnabledNIWSServerList.class)
        @ConditionalOnBean(SpringClientFactory.class)
        @ConditionalOnProperty(value = "ribbon.eureka.enabled", matchIfMissing = true)
        static class Defaults {}

        @ConditionalOnBean(EurekaClient.class)
        static class EurekaBeans {}

        @ConditionalOnProperty(value = "eureka.client.enabled", matchIfMissing = true)
        static class OnEurekaClientEnabled {}
    }
}
