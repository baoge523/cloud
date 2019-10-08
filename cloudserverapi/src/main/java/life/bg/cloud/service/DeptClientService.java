package life.bg.cloud.service;

import life.bg.cloud.entity.Dept;
import life.bg.cloud.fallback.DeptClientServiceFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * feign 负载均衡，是通过 注解+接口来实现的
 *
 *  调用:
 *   直接 @AutoWired
 *      private DeptClientService deptClientService;
 *
 *
 *   底层 会动态的生成该接口的对象，然后调用eureka中的微服务.
 *
 *   Feign 可以和 Hystrix 搭配使用
 *
 *   实现熔断处理的fallbackFactory接口
 *
 */
// 指定名称的微服务
//@FeignClient(name = "CLOUD-SERVER-PROVIDER")
@FeignClient(name = "CLOUD-SERVER-PROVIDER",fallbackFactory = DeptClientServiceFallbackFactory.class)
public interface DeptClientService {


    @PostMapping("/dept/add")
    boolean post(Dept dept);

    @GetMapping("/dept/get/{id}")
    Dept get(@PathVariable("id") int id);

    @GetMapping("/dept/list")
    List<Dept> list();

}
