package life.bg.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard  // 开启 HystrixDashboard 监控可视化 需要监控拥有 actuator依赖的项目
public class Dashboard9002 {
    public static void main(String[] args) {
        SpringApplication.run(Dashboard9002.class,args);
    }
}
