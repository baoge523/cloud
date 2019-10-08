## 学习SpringCloud


#### eureka 集群
**修改本机的域名映射**
> windows10下: C:\Windows\System32\drivers\etc下的hosts文件

映射规则:
> 127.0.0.1       eureka7001.com

> 127.0.0.1       eureka7002.com

如果修改hosts文件时，没有权限,[参考](https://jingyan.baidu.com/article/624e7459b194f134e8ba5a8e.html)

##### eureka集群出现的问题:
> 在绑定其他eureka服务的时候：通过defaultZone：指定其他的eureka的访问地址时，
> 不能使用default-zone：来指定，否则会出错

##### 服务提供者注入到eureka中遇到的问题
> defaultZone: http://eureka7002.com:7002/eureka

不能用
> defaultZone: http://eureka7002.com:7002 在服务注册是，会失败

#### Ribbon
**Ribbon用来做负载均衡，是一个客服端的负载均衡框架**
> Ribbon 需要依赖 eureka ，而 eureka 又依赖 config

> 所以引入Ribbon需要导入三个依赖

> 修改application.yml 添加eureka服务的访问地址，同时不让自己注入到eureka中

> 注意: 在重构的时候，需要注意: 微服务的名称 == ip+port

> 例如：http://localhost:8001 == http://微服务名称(大写)

> @LoadBalance 作用在config类中的RestTemplate上，表示负载均衡

> 主启动类上面需要添加 @EnableEurekaClient

**Ribbon 的负载均衡算法:**

1、RandomRule 随机

2、RoundRobinRule 轮询

3、RetryRule 先按照RoundRobinRule的策略获取服务，如果获取服务失败，则在指定时间内会进行重试，获取可用的服务

4、AvailabilityFilteringRule 会先过滤由于多次访问故障而处于短路器跳闸状态的服务，还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问。

5、WeightedResponseTimeRule 根据平均响应时间计算所有服务的权重，响应时间越快服务权重越大，被选中的概率越大。刚启动时使用RoundRobinRule策略，等统计信息足够了，然后使用WeightedResponseTimeRule。

6、BestAvailableRule 会先过滤掉多次访问故障而处于短路跳闸状态的服务，然后选择一个并发量最小的服务。

7、ZoneAvoidanceRule 默认规则，复合判断server所在区域的性能和server的可用性选择服务器。

我们修改成指定的负载均衡算法时，只需要向IOC容器中注入指定算法即可。

```java
@Bean
public RandomRule randomRule(){
    return new RandomRule();
}
```
当我们使用@RibbonClient(name="CLOUD-SERVER-PROVIDER",configuration=MyRule.class)
其中，MyRule是一个配置类(@Configuration),并且该类不在ComponentScan的扫描路径下，在MyRule中注入
提供的负载算法或者自定义的负载算法。

**Ribbon + RestTemplate 实现服务的负载均衡**

#### Feign 同样也是客户端的负载均衡框架
> Feign的使用模式是: 注解 + 接口，底层通过动态代理创建其对象并调用eureka中的微服务

```java
// 指定eureka中的微服务名称
@FeignClient(name = "CLOUD-SERVER-PROVIDER")
public interface DeptClientService {

    @PostMapping("/dept/add")
    boolean post(Dept dept);

    @GetMapping("/dept/get/{id}")
    Dept get(@PathVariable("id") int id);

    @GetMapping("/dept/list")
    List<Dept> list();

}
```
消费者也需要通过Eureka来获取其中的微服务，所以需要配置去哪里请求服务
```yml
server:
  port: 80
  
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka,http://eureka7003.com:7003/eureka
    register-with-eureka: false  # 不将自己注入到eureka中
```
启动类上需要同时使用
> @EnableEurekaClient

> @EnableFeignClient

#### Hystrix断路器(作用于服务端)
> 是一种微服务链路的保护机制

操作步骤:
导入对应的Hystrix的依赖

服务熔断(保险丝): 当运行程序中遇到异常，立马做相应的处理。

服务降级: 当前服务已经不提供服务了，但是还有客户在访问，此时我们需要告诉客户当前服务已经停止了。
(当服务重新启动后，又可以访问了)

服务降级和服务熔断都可以使用以下两种方式来实现


**服务降级(熔断)处理方式1(作用在服务端)**
>在可能会出现异常的方法上面添加一个
>@HystrixCommand(fallbackMethod="methodName")
>表示在该方法出现异常时，就会调用指定的fallbackMethod方法

> 这种方式的缺点: 每一个可能需要熔断(降级)处理的方法都需要添加@HystrixCommand注解，
> 还需编写对应的fallback方法

**服务降级(熔断)处理方式2(作用在客户端)**
> 通过 Feign + Hystrix 的搭配使用，在接口上指定一个FallbackFactory的实现类
```java
@FeignClient(name = "CLOUD-SERVER-PROVIDER",fallbackFactory = DeptClientServiceFallbackFactory.class)
```
注意: 修改了通用的API需要先clean，然后再install
```java
@Component // 需要将该FallbackFactory的实现类注入到IOC中
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            
            @Override
            public Dept get(int id) {
                return new Dept(id,"没有查找到指定id="+id+"的部门","no this dept id in db");
            }
        };
    }
}
```
最后在消费方(客服端)的配置文件中开启feign.hystrix的配置(重要)
```yml
feign:
  hystrix:
     enabled: true  
```

**hystrix的服务器的启动类上面需要添加@EnableCircuitBreaker**
> 表示开启 Hystrix熔断器

Hystrix Dashboard (监控系统可视化)

> 新建模块，导入依赖
```pom
    <!-- spring-cloud-starter-hystrix-dashboard -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
        <version>1.3.5.RELEASE</version>
    </dependency>

    <!-- spring-cloud-starter-hystrix -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix</artifactId>
        <version>1.3.5.RELEASE</version>
    </dependency>
```
application.yml 中指定端口

启动类上添加 @EnableHystrixDashboard 注解
注意: 如果想被 HystrixDashboard 监控，被监控的服务提供者必须要拥有 spring-boot-starter-actuator 依赖

注意: Dashboard的访问路径:  http://ip:port/hystrix

**如何监控查看信息呢?**

格式:

单个 http://被监控服务的ip:port/hystrix.stream

集群 http://被监控服务的ip:port/hystrix.stream?cluster=[clusterName]

注意:

 只有拥有@HystrixCommand注解时，单个查看是才会有data数据，不然就会ping，而没有data数据
 多个查看时，加载不出信息

#### zuul(网关)
