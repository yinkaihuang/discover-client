package org.springframework.cloud.zookeeper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.RetryPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnZookeeperEnabled
@EnableConfigurationProperties
public class ZookeeperAutoConfiguration {
    private static final Log log = LogFactory.getLog(ZookeeperAutoConfiguration.class);
    @Autowired(
        required = false
    )
    private EnsembleProvider ensembleProvider;

    public ZookeeperAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperProperties zookeeperProperties() {
        return new ZookeeperProperties();
    }

    @Bean(
        destroyMethod = "close"
    )
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework(RetryPolicy retryPolicy, ZookeeperProperties properties) throws Exception {
        Builder builder = CuratorFrameworkFactory.builder();
        if (this.ensembleProvider != null) {
            builder.ensembleProvider(this.ensembleProvider);
        } else {
            builder.connectString(properties.getConnectString());
        }

        CuratorFramework curator = builder.retryPolicy(retryPolicy).build();
        curator.start();
        log.trace("blocking until connected to zookeeper for " + properties.getBlockUntilConnectedWait() + properties.getBlockUntilConnectedUnit());
        curator.blockUntilConnected(properties.getBlockUntilConnectedWait(), properties.getBlockUntilConnectedUnit());
        log.trace("connected to zookeeper");
        return curator;
    }

    @Bean
    @ConditionalOnMissingBean
    public RetryPolicy exponentialBackoffRetry(ZookeeperProperties properties) {
        return new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries(), properties.getMaxSleepMs());
    }

    @Configuration
    @ConditionalOnClass({Endpoint.class})
    protected static class ZookeeperHealthConfig {
        protected ZookeeperHealthConfig() {
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnBean({CuratorFramework.class})
        @ConditionalOnEnabledHealthIndicator("zookeeper")
        public ZookeeperHealthIndicator zookeeperHealthIndicator(CuratorFramework curator) {
            return new ZookeeperHealthIndicator(curator);
        }
    }
}