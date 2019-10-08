package life.bg.cloud;

import life.bg.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author 宝哥
 * @date 2019/10/3
 * TODO
 */
@EnableEurekaClient
@SpringBootApplication
// 注意 MyRule.class必须是不在ComponentScan路径下的
@RibbonClient(name = "CLOUD-SERVER-PROVIDER",configuration = MyRule.class)
public class Consumer {
    public static void main(String[] args) {
        SpringApplication.run(Consumer.class,args);
    }
}
