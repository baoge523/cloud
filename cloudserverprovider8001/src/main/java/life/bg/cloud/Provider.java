package life.bg.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 宝哥
 * @date 2019/10/2
 *
 *   服务提供者，需要注册到注册中心(Eureka)
 *     步骤:
 *         1、添加pom依赖和修改application配置文件
 *         2、开启Eureka的客户端 @EnableEurekaClient
 *         3、业务操作
 *
 *
 *
 */
@EnableDiscoveryClient    // 开启发现客户端
@EnableEurekaClient       // 开启eureka客户端
@SpringBootApplication
public class Provider {
    public static void main(String[] args) {
        // 启动类
        SpringApplication.run(Provider.class,args);
    }
}
