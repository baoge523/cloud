## 学习SpringCloud


#### eureka 集群
**修改本机的域名映射**
> windows10下: C:\Windows\System32\drivers\etc下的hosts文件

映射规则:
> 127.0.0.1       eureka7001.com

> 127.0.0.1       eureka7002.com

如果修改hosts文件时，没有权限,[参考](https://jingyan.baidu.com/article/624e7459b194f134e8ba5a8e.html)

###### eureka集群出现的问题:
> 在绑定其他eureka服务的时候：通过defaultZone：指定其他的eureka的访问地址时，
> 不能使用default-zone：来指定，否则会出错

#### Ribbon
**Ribbon用来做负载均衡，是一个客服端的负载均衡框架**
> Ribbon 需要依赖 eureka ，而 eureka 又依赖 config

> 所以引入Ribbon需要导入三个依赖

> 修改application.yml 添加eureka服务的访问地址，同时不让自己注入到eureka中

> 注意: 在重构的时候，需要注意: 微服务的名称 == ip+port

> 例如：http://localhost:8001 == http://微服务名称(大写)

> @LoadBalance 作用在config类中的RestTemplate上，表示负载均衡

> 主启动类上面需要添加 @EnableEurekaClient
