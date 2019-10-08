package life.bg;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRule {

    @Bean
    public IRule iRule(){

//        return new RoundRobinRule(); // 轮询
//       return new RandomRule(); // 随机
        return new RoundRobinRule_zy(); // 自定义的 一个轮询5次
    }

}
