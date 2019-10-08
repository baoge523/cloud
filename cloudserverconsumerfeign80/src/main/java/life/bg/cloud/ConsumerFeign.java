package life.bg.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author 宝哥
 * @date 2019/10/3
 * TODO
 */
@EnableEurekaClient
@SpringBootApplication
// 开启 Feign 的客户端，其中@FeignClient标注的接口就会被动态代理
@EnableFeignClients
public class ConsumerFeign {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeign.class,args);
    }
}
