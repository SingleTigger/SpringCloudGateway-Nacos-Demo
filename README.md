# SpringCloudGateway-Demo

## SpringCloud Gateway简介
SpringCloud Gateway该项目提供了一个用于在Spring MVC之上构建API网关的库，建立在Spring Framework 5，Project Reactor和Spring Boot 2.0之上。Spring Cloud Gateway旨在提供一种简单而有效的方法来路由到API，并为它们提供跨领域的关注，例如：安全性，监视/指标和弹性。

## 关于Demo
**本Demo提供一个单机的网关例子，注册中心使用阿里巴巴的Nacos。
由于Nacos也提供配置中心，因此结合网关更容易做到动态配置，可以在配置中启动。**

支持以下功能

- 基于Nacos动态配置路由
- IP黑名单过滤
- 鉴权过滤器
- 断路器配置，回调
- 支持修改请求body
- 支持修改响应body
- 自定义异常处理器

## 依赖&版本

- Maven
- JDK8+
- SpringBoot 2.2.0
- SpringCloud Greenwich.SR3

## 主要目录结构


- config 配置文件，包括自定义异常处理的配置以及动态路由配置
- exception
    异常处理的具体实现
- filter
   - factory 局部拦截器，包含修改body，鉴权等
   - global 全局拦截器，包含IP拦截
- route 动态路由的实现
- service IP拦截以及鉴权的具体实现
- predicate 预言