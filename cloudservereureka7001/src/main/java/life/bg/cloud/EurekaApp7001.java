package life.bg.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 宝哥
 * @date 2019/10/3
 * TODO
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApp7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApp7001.class,args);
    }
}
