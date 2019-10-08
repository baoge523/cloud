package life.bg.cloud.config;

import com.netflix.loadbalancer.RandomRule;
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

    /**
     *
     * 通过 RibbonClient(name="CLOUD-SERVER-PROVIDER",configuration=MyRule.class)
     *
     * MyRule是一个配置类，且不在 ComponentScan下
     *
     * Ribbon 的负载均衡算法
     * @return
     */
//    @Bean
//    public RandomRule randomRule(){
//        return new RandomRule();
//    }

}
