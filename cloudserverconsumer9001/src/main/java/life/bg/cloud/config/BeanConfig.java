package life.bg.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 宝哥
 * @date 2019/10/3
 * TODO
 */
@Configuration
public class BeanConfig {

    @Bean
    @LoadBalanced // 负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
