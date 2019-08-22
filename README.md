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
    <version>1.0-SNAPSHOT</version>
</dependency>

3.配置使用的注册中心
register.type=k8s (这里支持k8s/zookeeper/eureka)

4.在启动类上面添加@EableRegisterClient
```