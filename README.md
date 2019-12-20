# SpringCloudGateway-Demo

**项目提供一个单机SpringCloud 网关例子，注册中心使用阿里巴巴的Nacos，由于Nacos也提供配置中心，因此结合网关更容易做到动态配置。包含以下功能（部分功能只作Demo，需要自己完善）**

- 基于Nacos动态配置路由
- IP黑名单过滤
- 鉴权过滤器
- 断路器配置，执行fallback操作
- 支持修改请求body
- 支持修改响应body
- 自定义异常处理器

