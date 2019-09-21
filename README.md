# 注册中心

## 功能
```
通过配置动态切换注册中心
```
## 使用说明
```
1.在discover-core模块下面执行mvn install


2.添加maven依赖包
<dependency>
    <groupId>cn.bucheng.discover</groupId>
    <artifactId>discover-core</artifactId>
    <version>2.0-SNAPSHOT</version>
</dependency>

3.配置使用的注册中心
register.type=k8s (这里支持k8s/zookeeper/eureka)

4.在启动类上面添加@EnableRegisterClient
```

## 问题
```
1.上k8s可能出现下面问题
io.fabric8.kubernetes.client.KubernetesClientException: Failure executing: GET at: https://kubernetes.default.svc/api/v1/namespaces/basic-app/endpoints/server-b. Message: Forbidden!Configured service account doesn't have access. Service account may have been revoked. endpoints "server-b" is forbidden: User "system:serviceaccount:basic-app:default" cannot get endpoints in the namespace "basic-app".

解决办法：
1. 在Deployment文件中添加 serviceAccountName: application
2.再执行 kubectl -n basic-app create sa application && kubectl create clusterrolebinding application --clusterrole cluster-admin --serviceaccount=basic-app:application

```