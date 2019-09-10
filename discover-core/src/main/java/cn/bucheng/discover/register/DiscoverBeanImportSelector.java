package cn.bucheng.discover.register;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import cn.bucheng.discover.annotation.EnableRegisterClient;
import cn.bucheng.discover.util.EnvironmentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.kubernetes.discovery.KubernetesDiscoveryClientAutoConfiguration;
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
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ：yinchong
 * @create ：2019/9/10 9:16
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@SuppressWarnings("all")
public class DiscoverBeanImportSelector implements ImportSelector {
    public static final String REGISTER_TYPE = "register.type";
    public static final String EUREKA = "EUREKA";
    public static final String ZOOKEEPER = "ZOOKEEPER";
    public static final String K_8_S = "K8S";

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        List<String> candidateComponents = new LinkedList<>();
        if (!annotationMetadata.hasAnnotation(EnableRegisterClient.class.getName()))
            return null;
        String registerType = EnvironmentUtils.getValue(REGISTER_TYPE);
        if (Strings.isBlank(registerType)) {
            log.error("not get register.type from environment");
            throw new RuntimeException("please set register.type");
        }
        registerType = registerType.toUpperCase();
        switch (registerType) {
            case EUREKA:
                log.info("register use eureka");
                registerEureka(candidateComponents);
                break;
            case ZOOKEEPER:
                log.info("register use zookeeper");
                registerZookeeper(candidateComponents);
                break;
            case K_8_S:
                log.info("register use k8s");
                registerK8s(candidateComponents);
                break;
        }
        return candidateComponents.toArray(new String[candidateComponents.size()]);
    }


    private void registerEureka(List<String> candidateList) {
        candidateList.add(EurekaClientAutoConfiguration.class.getName());
        candidateList.add(RibbonEurekaAutoConfiguration.class.getName());
    }

    private void registerZookeeper(List<String> candidateList) {
        candidateList.add(ZookeeperDiscoveryAutoConfiguration.class.getName());
        candidateList.add(ZookeeperDiscoveryClientConfiguration.class.getName());
        candidateList.add(CuratorServiceDiscoveryAutoConfiguration.class.getName());
        candidateList.add(ZookeeperServiceRegistryAutoConfiguration.class.getName());
        candidateList.add(ZookeeperAutoConfiguration.class.getName());
        candidateList.add(ZookeeperAutoServiceRegistrationAutoConfiguration.class.getName());
        candidateList.add(DependencyRibbonAutoConfiguration.class.getName());
        candidateList.add(DependencyFeignClientAutoConfiguration.class.getName());
        candidateList.add(DependencyRestTemplateAutoConfiguration.class.getName());
        candidateList.add(DependencyWatcherAutoConfiguration.class.getName());
        candidateList.add(RibbonZookeeperAutoConfiguration.class.getName());
    }

    private void registerK8s(List<String> candidateList) {
        candidateList.add(KubernetesDiscoveryClientAutoConfiguration.class.getName());
    }


}
