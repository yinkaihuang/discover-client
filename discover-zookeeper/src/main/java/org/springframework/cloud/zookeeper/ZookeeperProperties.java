package org.springframework.cloud.zookeeper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.TimeUnit;

@Validated
@ConfigurationProperties("spring.cloud.zookeeper")
public class ZookeeperProperties {
    private String connectString = "localhost:2181";
    private boolean enabled = true;
    private Integer baseSleepTimeMs = 50;
    private Integer maxRetries = 10;
    private Integer maxSleepMs = 500;
    private Integer blockUntilConnectedWait = 10;
    private TimeUnit blockUntilConnectedUnit;

    public ZookeeperProperties() {
        this.blockUntilConnectedUnit = TimeUnit.SECONDS;
    }

    public String getConnectString() {
        return this.connectString;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Integer getBaseSleepTimeMs() {
        return this.baseSleepTimeMs;
    }

    public Integer getMaxRetries() {
        return this.maxRetries;
    }

    public Integer getMaxSleepMs() {
        return this.maxSleepMs;
    }

    public Integer getBlockUntilConnectedWait() {
        return this.blockUntilConnectedWait;
    }

    public TimeUnit getBlockUntilConnectedUnit() {
        return this.blockUntilConnectedUnit;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public void setMaxSleepMs(Integer maxSleepMs) {
        this.maxSleepMs = maxSleepMs;
    }

    public void setBlockUntilConnectedWait(Integer blockUntilConnectedWait) {
        this.blockUntilConnectedWait = blockUntilConnectedWait;
    }

    public void setBlockUntilConnectedUnit(TimeUnit blockUntilConnectedUnit) {
        this.blockUntilConnectedUnit = blockUntilConnectedUnit;
    }
}